package com.mingspy.walee.analysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.mingspy.walee.core.Question;

/**
 * 分析器总管，负责调用其他分析器。这里只是用了一个Composed模式，方便调用。
 * 
 * @author xiuleili
 * 
 */
public class AnalyzerList extends LinkedList<IQAnalyzer> implements IQAnalyzer {

	public AnalyzerList(){
		// add analyzers, the order is very important, as 
		// some analyzer is depended to another has handled.
		add(QAnalyzerFactory.getTokenAnalyzer());
		add(QAnalyzerFactory.getNameEntityAnalyzer());
		add(QAnalyzerFactory.getCategoryAnalyzer());
		add(QAnalyzerFactory.getLATAnalyzer());
	}
	
	@Override
	public boolean analysis(Question question) {
		for(IQAnalyzer analyzer : this){
			if(!analyzer.analysis(question)){
				return false;
			}
		}
		
		return true;
	}

}
