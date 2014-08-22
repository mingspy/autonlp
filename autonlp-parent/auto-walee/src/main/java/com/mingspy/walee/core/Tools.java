package com.mingspy.walee.core;

import java.util.Comparator;
import java.util.List;

import com.mingspy.jseg.CppTokenizer;
import com.mingspy.jseg.ITokenizer;
import com.mingspy.jseg.Token;

public class Tools {

	public static double getIDF(String word) {
		// TODO Auto-generated method stub
		return 1.0;
	}
	
	public static double totalIDF(List<Token> qTokens){
    	double total = 0;
    	for (Token qToken : qTokens) {
            //忽略问题中长度为1的词, 忽略标点符号。
            if (qToken.word.length() < 2 || qToken.nature.equalsIgnoreCase("w")) {
                continue;
            }
            double idf = Tools.getIDF(qToken.word);
            total += idf;
    	}
    	
    	return total;
    }

	private static final ITokenizer _tokenizer = new CppTokenizer();
	public static List<Token> POSTagging(String content) {
		return _tokenizer.POSTagging(content);
	}
		
	public static List<String> split(String str){
		return _tokenizer.uniGramSplit(str);
	}

	private static  class ASCScoreComparator implements Comparator<ScoreObj>{

		@Override
		public int compare(ScoreObj o1, ScoreObj o2) {
			double delta = o1.getScore() - o2.getScore();
			return delta < 0?-1:(delta>0.000000001?1:0);
		}
		
	}
	
	private static  class DSCScoreComparator implements Comparator<ScoreObj>{

		@Override
		public int compare(ScoreObj o1, ScoreObj o2) {
			double delta = o2.getScore() - o1.getScore();
			return delta < 0?-1:(delta>0.000000001?1:0);
		}
		
	}
	
	public static final ASCScoreComparator ASC_SCORE_COMPARAROTR = new ASCScoreComparator();
	public static final DSCScoreComparator DSC_SCORE_COMPARAROTR = new DSCScoreComparator();
}
