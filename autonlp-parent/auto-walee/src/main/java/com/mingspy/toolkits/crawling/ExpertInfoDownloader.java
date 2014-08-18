package com.mingspy.toolkits.crawling;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import com.mingspy.utils.html.HttpDownloader;
import com.mingspy.utils.io.LineFileWriter;

/**
 * 搜狐汽车专家信息抓取
 * 
 * @author xiuleili
 * 
 */
public class ExpertInfoDownloader {
	public static class Expert {
		public String name; // 姓名
		public int answers; // 回答数
		public double adopted; // 采纳率

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAnswers() {
			return answers;
		}

		public void setAnswers(int answers) {
			this.answers = answers;
		}

		public double getAdopted() {
			return adopted;
		}

		public void setAdopted(double addopted) {
			this.adopted = addopted;
		}

		@Override
		public String toString() {
			return name + "\t" + answers + "\t" + adopted;
		}

	}

	private static final String SAA_EXPERT_URL_PREFIX = "http://ask.auto.sohu.com/allexperts.shtml?expertCatagory=&brandId=&page=";
	private static final String BITAUTO_EXPERT_URL_PREFIX = "http://ask.bitauto.com/expertlist/p";
	private static final String BITAUTO_URL_PREFIX = "http://ask.bitauto.com";

	public static void crawleSAAExperts(String outpath)
			throws XPatherException, UnsupportedEncodingException,
			FileNotFoundException {
		HttpDownloader downloader = new HttpDownloader();
		HtmlCleaner cleaner = new HtmlCleaner();
		List<Expert> experts = new ArrayList<Expert>(200);
		// 目前只有40页专家数据，每页5个
		for (int i = 1; i <= 40; i++) {
			String content = downloader.downloadAsString(SAA_EXPERT_URL_PREFIX
					+ i, "gb2312");
			if (content == null) {
				System.out.println("faild to download page:" + i);
				return;
			}
			TagNode root = cleaner.clean(content);
			Object[] dls = root
					.evaluateXPath("//ul[@class='expertise_con']//li[@class='con']//div[@class='expert']//dl");
			for (Object obj : dls) {
				TagNode dl = (TagNode) obj;
				Object[] dt = dl.evaluateXPath("//dt//a");
				Expert expert = new Expert();
				expert.setName(((TagNode) dt[0]).getText().toString().trim());
				Object[] dds = dl.evaluateXPath("//dd");
				String answers = ((TagNode) dds[3])
						.findElementByName("em", false).getText().toString()
						.trim();
				expert.setAnswers(Integer.parseInt(answers));
				String adopted = ((TagNode) dds[4])
						.findElementByName("em", false).getText().toString()
						.trim();
				adopted = adopted.substring(0, adopted.length() - 1);
				expert.setAdopted(Double.parseDouble(adopted));
				System.out.println(expert);
				experts.add(expert);
			}
		}

		LineFileWriter writer = new LineFileWriter(outpath);
		writer.writeLine(experts);
		writer.close();
	}

	public static void crawleBitAutoExperts(String outpath)
			throws XPatherException, UnsupportedEncodingException,
			FileNotFoundException {
		HttpDownloader downloader = new HttpDownloader();
		HtmlCleaner cleaner = new HtmlCleaner();
		List<Expert> experts = new ArrayList<Expert>(200);

		// 目前只有7页专家数据，每页5个
		for (int i = 1; i <= 7; i++) {
			String content = downloader.downloadAsString(
					BITAUTO_EXPERT_URL_PREFIX + i, "gb2312");
			if (content == null) {
				System.out.println("faild to download page:" + i);
				return;
			}
			TagNode root = cleaner.clean(content);
			Object[] dls = root
					.evaluateXPath("//div[@id='ul_id_1_box_0']//ul//li//div[@class='txtbox']//p[@class='name']//a");
			for (Object obj : dls) {
				TagNode a = (TagNode) obj;
				String href = a.getAttributeByName("href").trim();
				String expertDetail = downloader
						.downloadAsString(BITAUTO_URL_PREFIX + href);

				TagNode expertDetailNode = cleaner.clean(expertDetail);
				Object[] expertNode = expertDetailNode
						.evaluateXPath("//div[@class='expCon']");
				String name = ((TagNode) expertNode[0])
						.findElementByName("p", false).getText().toString()
						.trim();
				Object[] nums = ((TagNode) expertNode[0])
						.evaluateXPath("//ul[@class='numUl']//li//p[@class='p2']");
				String ans = ((TagNode) nums[0]).getText().toString().trim();
				int answers = Integer.parseInt(ans);
				String adop = ((TagNode) nums[2]).getText().toString().trim();
				int adopeted = Integer.parseInt(adop);

				Expert expert = new Expert();
				expert.setName(name);
				expert.setAnswers(answers);
				if (answers != 0) {
					expert.setAdopted((int)((adopeted*100.0) / answers));
				}
				experts.add(expert);
			}
		}

		LineFileWriter writer = new LineFileWriter(outpath);
		writer.writeLine(experts);
		writer.close();
	}

	public static void main(String[] args) throws UnsupportedEncodingException,
			FileNotFoundException, XPatherException {
		// crawleSAAExperts("e:/tmp/autohome/saa_expert_info.txt");
		crawleBitAutoExperts("e:/tmp/autohome/bitauto_expert_info.txt");
	}
}
