package com.mingspy.walee.answer.scorer.evidence;

import org.apache.log4j.Logger;

import com.mingspy.utils.Range;
import com.mingspy.utils.RangeKeeper;
import com.mingspy.utils.SuffixTree;
import com.mingspy.walee.answer.IEvidenceScorer;
import com.mingspy.walee.core.Configuration;
import com.mingspy.walee.core.Evidence;
import com.mingspy.walee.core.Question;

/**
 * LCS(common substring)用于检测公共字符串的长度。
 * @author xiuleili
 *
 */
public class CSEvidenceScorer implements IEvidenceScorer {

	public static final String WEIGHT_LCSSCORER = "WEIGHT_LCSSCORER";

	private static final Logger LOG = Logger
			.getLogger(CSEvidenceScorer.class);
	
	@Override
	public double score(Question question, Evidence evidence) {
		LOG.debug("*************************");
		LOG.debug("Evidence 最长公共子串评分开始");
		

		// title最长字符串分数*2
		double score = score(question.getContent(), evidence.getTitle()) * 2;
		score += score(question.getContent(), evidence.getContent());
		
		// 归一化得分
		score *= Configuration.instance().getDouble(WEIGHT_LCSSCORER, 1.0) / 3;
		LOG.debug("Evidence 最长公共子串评分结束, 得分："+score);
		LOG.debug("*************************");
		return score;
		
	}
	
	public static double score(String s1, String s2){
		// 得分 = 公共字符串长度 / (两文本长度之和 - 公共字符串长度 )
		SuffixTree st = new SuffixTree(s1);
		st.addString(s2);
		RangeKeeper keeper = st.getLastCommonRanges();
		int len = 0;
		for(Range r : keeper.getRanges()){
			if(r.length() > 1)
				len += r.length();
		}
		return len / (s1.length() + s2.length() - len + 0.00001);
	}

}
