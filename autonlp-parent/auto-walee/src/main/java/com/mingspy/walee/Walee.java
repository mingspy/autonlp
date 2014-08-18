package com.mingspy.walee;

import org.apache.log4j.Logger;

import com.mingspy.utils.MSTimer;
import com.mingspy.walee.analysis.IQAnalyzer;
import com.mingspy.walee.answer.IAnswerGenerator;
import com.mingspy.walee.answer.IAnswerSynthesizer;
import com.mingspy.walee.core.Question;

public class Walee implements IChatting {

	private static final Logger LOG = Logger.getLogger(Walee.class);

	private IQAnalyzer questionAnalyzer;
	private IAnswerGenerator answerGenerator;
	private IAnswerSynthesizer answerSynthesizer;

	@Override
	public Question answer(String questionStr) {
		Question question = new Question();
		question.setContent(questionStr);

		LOG.debug("----问题分析开始：[" + questionStr + "]");
		MSTimer timer = new MSTimer();
		questionAnalyzer.analysis(question);
		LOG.debug("----问题分析结束：" + timer);

		LOG.debug("====答案生成开始：[" + questionStr + "]");
		timer.restart();
		answerGenerator.generate(question);
		LOG.debug("====答案生成结束：" + timer);
		
		LOG.debug("====答案合成开始：[" + questionStr + "]");
		timer.restart();
		answerSynthesizer.synthesis(question);
		LOG.debug("====答案合成结束：" + timer);
		return question;
	}

	public IQAnalyzer getQuestionAnalyzer() {
		return questionAnalyzer;
	}

	public void setQuestionAnalyzer(IQAnalyzer questionAnalyzer) {
		this.questionAnalyzer = questionAnalyzer;
	}

	public IAnswerGenerator getAnswerGenerator() {
		return answerGenerator;
	}

	public void setAnswerGenerator(IAnswerGenerator answerGenerator) {
		this.answerGenerator = answerGenerator;
	}

	public IAnswerSynthesizer getAnswerSynthesizer() {
		return answerSynthesizer;
	}

	public void setAnswerSynthesizer(IAnswerSynthesizer answerSynthesizer) {
		this.answerSynthesizer = answerSynthesizer;
	}

}
