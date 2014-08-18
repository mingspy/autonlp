package com.mingspy.walee.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户的问题。
 * @author xiuleili
 *
 */
public class Question {
	/**
	 * 分词结果
	 */
	public static final String TOKENS = "tokens";
	/**
	 * 问题分类
	 */
	public static final String CATEGORY = "category";
	/**
	 * 答案类型
	 */
	public static final String ANSWER_TYPE = "answer_type";
	/**
	 * 关系列表：
	 */
	public static final String RELATIONS = "relations";
	
	/**
	 * 问题的内容
	 */
	private String content;
	/**
	 * 问题的答案
	 */
	private List<Answer> answers;
	/**
	 * 问题分析的一些属性。为了支持向后扩展，故写成map方式。
	 */
	private Map<String,Object> properties = new HashMap<String, Object>();
	
	/**
	 * 获取问题的某属性。
	 * @param key
	 * @return
	 */
	public Object getProperty(String key){
		return properties.get(key);
	}
	
	/**
	 * 设置问题的属性。
	 * @param key
	 * @param val
	 */
	public void setProperty(String key, Object val){
		properties.put(key, val);
	}
	
	// -----------------------gets/sets-----------------------------
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

}
