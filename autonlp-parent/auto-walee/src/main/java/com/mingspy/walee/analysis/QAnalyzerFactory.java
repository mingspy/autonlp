package com.mingspy.walee.analysis;

public final class QAnalyzerFactory {
	private static final TokenAnalyzer tokenAnalyzer = new TokenAnalyzer();
	private static final NameEntityAnalyzer nameEntityAnalyzer = new NameEntityAnalyzer();
	private static final CategoryAnalyzer categoryAnalyzer = new CategoryAnalyzer();
	
	public static TokenAnalyzer getTokenAnalyzer(){
		return tokenAnalyzer;
	}
	
	public static NameEntityAnalyzer getNameEntityAnalyzer(){
		return nameEntityAnalyzer;
	}
	
	public static CategoryAnalyzer getCategoryAnalyzer(){
		return categoryAnalyzer;
	}
}
