package com.mingspy.walee.core;


/**
 * 答案的证据。用于支持某答案的文本，问题等。
 * @author xiuleili
 *
 */
public class Evidence {
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 来源
	 */
	private String source;
	/**
	 * 得分
	 */
	private double score;
	/**
	 * 备注：主要用于记录得分记录，用于debug.
	 */
	private String notes;
	
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
