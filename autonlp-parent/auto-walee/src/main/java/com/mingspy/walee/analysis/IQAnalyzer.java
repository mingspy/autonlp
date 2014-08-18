package com.mingspy.walee.analysis;

import com.mingspy.walee.core.Question;

/**
 * 问题分析器接口。
 * @author xiuleili
 *
 */
public interface IQAnalyzer {
	/**
	 * 对问题进行分析，抽取出有用的信息，并存储在问题的properties中。
	 * @param question 待分析的问题。
	 * @return true 分析成功，false 分析失败。
	 */
	boolean analysis(Question question);
}
