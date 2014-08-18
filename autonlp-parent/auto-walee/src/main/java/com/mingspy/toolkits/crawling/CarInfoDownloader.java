package com.mingspy.toolkits.crawling;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import com.mingspy.utils.html.HttpDownloader;
import com.mingspy.utils.io.LineFileWriter;

public class CarInfoDownloader {
	private static final String CARINFO_AUTO_SOHU_URL = "http://db.auto.sohu.com";
	
	public  enum CarPOS{
		/**
		 * 品牌名称
		 */
		BRAND("cb","汽车品牌"),
		/**
		 * 子品牌
		 */
		SUBBRAND("csb","汽车子品牌"),
		/**
		 * 车型
		 */
		MODEL("cm","车型");
		private String name = null;
		private String describe = null;
		private CarPOS(String name, String describe){
			this.name = name;
			this.describe = describe;
		}
		@Override
		public String toString() {
			return name;
		}

	}
	
	public  enum CarType{
		UDF("未定义"),
		MINI("微型车"), 
		SMALL("小型车"),
		COMPACT("紧凑型车"),
		MIDDLE("中型车"),
		UPPER_MIDSIZE("中大型车"),
		LUXURY("豪华车"),
		MPV("MPV"),
		SUV("SUV"),
		SPORTS("跑车"),
		NEW_ENERGY("新能源车");
		private String name = null;

		private CarType(String name){
			this.name = name;
		}
		public String getName() {
			return name;
		}
	
		@Override
		public String toString() {
			return name;
		}
		
		public static CarType fromName(String name){
			CarType [] ts = CarType.class.getEnumConstants();
			for(CarType t: ts){
				if(t.getName().equalsIgnoreCase(name)){
					return t;
				}
			}
			return UDF;
		}

	}
	
	public static class CarInfo{
		private String name;
		private CarPOS pos;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public CarPOS getPos() {
			return pos;
		}
		public void setPos(CarPOS pos) {
			this.pos = pos;
		}
		@Override
		public String toString() {
			return name+"\t"+pos;
		}
		
	}
	
	public static class CarBrand extends CarInfo{
		public CarBrand(){
			setPos(CarPOS.BRAND);
		}
		private List<CarSubBrand> subBrands = new ArrayList<CarSubBrand>();

		public List<CarSubBrand> getSubBrands() {
			return subBrands;
		}

		public void setSubBrands(List<CarSubBrand> subBrands) {
			this.subBrands = subBrands;
		}

		public void addSubBrand(CarSubBrand subBrand) {
			subBrands.add(subBrand);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for(CarSubBrand b : subBrands){
				sb.append(b.toString());
				sb.append("\n");
			}
			return super.toString() + "\n" + sb.toString();
		}
		
		
		
	}
	
	public static class CarSubBrand extends CarInfo{
		public CarSubBrand(){
			setPos(CarPOS.SUBBRAND);
		}
		private List<CarModel> subModles = new ArrayList<CarModel>();
		public List<CarModel> getSubModles() {
			return subModles;
		}

		public void setSubModles(List<CarModel> subModles) {
			this.subModles = subModles;
		}

		public void addModel(CarModel carModel) {
			subModles.add(carModel);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for(CarModel b : subModles){
				sb.append(b.toString());
				sb.append("\n");
			}
			return super.toString() + "\n" + sb.toString();
		}
		
	}
	
	public static class CarModel extends CarInfo{
		public CarModel(){
			setPos(CarPOS.MODEL);
		}
		private CarType type;

		public CarType getType() {
			return type;
		}

		public void setType(CarType type) {
			this.type = type;
		}

		@Override
		public String toString() {
			return super.toString()+"\t"+type;
		}	
		
	}
	
	public static void crawlAutoSohuCarInfo(String outFolder) throws Exception{
		HttpDownloader downloader = new HttpDownloader();
		HtmlCleaner cleaner = new HtmlCleaner();	
		String htmlContent = downloader.downloadAsString(CARINFO_AUTO_SOHU_URL);
		TagNode htmlNode = cleaner.clean(htmlContent);
		Object [] category_mains = htmlNode.evaluateXPath("//div[@class=\"category_main\"]");
		List<CarBrand> brands = new LinkedList<CarBrand>();
		for(Object cat : category_mains){
			TagNode brandNode = (TagNode) cat;
			CarBrand brand = new CarBrand();
			brand.setName(brandNode.findElementByAttValue("class", "car_brand", true, false).getText().toString().trim());
			brands.add(brand);
			Object [] sub_brands = brandNode.evaluateXPath("//div[@class=\"meta_con\"]");
			for(Object meta:sub_brands){
				TagNode subbrandNode = (TagNode)meta;
				CarSubBrand subBrand = new CarSubBrand();
				Object[] a = subbrandNode.evaluateXPath("//div[@class=\"brand_name\"]//a");
				subBrand.setName(((TagNode)a[0]).getText().toString().trim());
				brand.addSubBrand(subBrand);
				Object [] models = subbrandNode.evaluateXPath("//ul//li");
				for(Object m : models){
					TagNode modelNode = (TagNode) m;
					Object[] objs = modelNode.evaluateXPath("//dl//dt//span");
					if(objs == null || objs.length == 0){
						System.err.println(modelNode.getName()+"->"+modelNode.getText().toString());
						continue;
					}
					modelNode = (TagNode)objs[0];
					CarModel carModel = new CarModel();
					Object [] modelName = modelNode.evaluateXPath("/span/a");
					
					if(modelName == null || modelName.length == 0){
						modelName = modelNode.evaluateXPath("/a");
					}
					carModel.setName(((TagNode)modelName[0]).getText().toString().trim());
					Object [] modelType = modelNode.evaluateXPath("/a");
					if(modelType != null && modelType.length > 0)
					carModel.setType(CarType.fromName(((TagNode)modelType[0]).getText().toString().trim()));
					subBrand.addModel(carModel);
				}
			}
		}
		
		LineFileWriter writer = new LineFileWriter(FilenameUtils.concat(outFolder, "carinfo.txt"));
		writer.writeLine(brands);
		writer.close();
		
		LineFileWriter dicwriter = new LineFileWriter(FilenameUtils.concat(outFolder, "carname.txt"));
		for(CarBrand b:brands){
			dicwriter.writeLine(b.getName()+"\t"+b.getPos());
			for(CarSubBrand sb : b.getSubBrands()){
				if(!sb.getName().equalsIgnoreCase(b.getName())){
					dicwriter.writeLine(sb.getName()+"\t"+sb.getPos());
					if(sb.getName().startsWith(b.getName())){
						dicwriter.writeLine(sb.getName().replace(b.getName(), "")+"\t"+sb.getPos());
					}
				}
				for(CarModel m : sb.getSubModles()){
					if(!m.getName().equalsIgnoreCase(sb.getName())){
						dicwriter.writeLine(m.getName()+"\t"+m.getPos());
						if(m.getName().startsWith(sb.getName())){
							dicwriter.writeLine(m.getName().replace(sb.getName(), "")+"\t"+m.getPos());
						}
					}
					
				}
			}
		}
		dicwriter.close();
	}
	
	
	public static void main(String[] args) throws Exception {
		crawlAutoSohuCarInfo("e:/tmp/sohu/");
	}

}
