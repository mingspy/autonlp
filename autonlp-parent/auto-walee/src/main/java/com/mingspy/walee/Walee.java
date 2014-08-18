package com.mingspy.walee;

import com.mingspy.walee.analysis.IQAnalysis;
import com.mingspy.walee.answer.IAnswerGenerator;
import com.mingspy.walee.answer.IAnswerSelector;
import com.mingspy.walee.core.Question;

public class Walee implements IChatting {

	private IQAnalysis question_analysor;
	private IAnswerGenerator answer_generator;
	private IAnswerSelector answer_selector;
	@Override
	public Question answer(String questionStr) {
		Question question = new Question();
		question.setContent(questionStr);
		question_analysor.analysis(question);
		answer_generator.generate(question);
		answer_selector.select(question);
		return question;
	}
	
	
	public IQAnalysis getQuestion_analysor() {
		return question_analysor;
	}
	public void setQuestion_analysor(IQAnalysis question_analysor) {
		this.question_analysor = question_analysor;
	}
	public IAnswerGenerator getAnswer_generator() {
		return answer_generator;
	}
	public void setAnswer_generator(IAnswerGenerator answer_generator) {
		this.answer_generator = answer_generator;
	}
	public IAnswerSelector getAnswer_selector() {
		return answer_selector;
	}
	public void setAnswer_selector(IAnswerSelector answer_selector) {
		this.answer_selector = answer_selector;
	}

}
