package com.mingspy.walee.core;

import java.util.List;

/**
 * 问题的答案。
 * 
 * 
 * @author xiuleili
 * 
 */
public class Answer {
	/**
	 * 答案的内容
	 */
	private String content;
	/**
	 * 答案的得分
	 */
	private double score;
	/**
	 * 答案的证据列表。支持获得该答案的所有引用的文本，资源。
	 */
	private List<Evidence> evidences;
	
	
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public List<Evidence> getEvidences() {
		return evidences;
	}
	public void setEvidences(List<Evidence> evidences) {
		this.evidences = evidences;
	}
}
