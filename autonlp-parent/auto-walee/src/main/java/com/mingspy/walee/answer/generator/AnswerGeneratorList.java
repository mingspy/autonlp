package com.mingspy.walee.answer.generator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.mingspy.walee.answer.IAnswerGenerator;
import com.mingspy.walee.core.Answer;
import com.mingspy.walee.core.Question;

public class AnswerGeneratorList extends LinkedList<IAnswerGenerator> implements IAnswerGenerator {

	public AnswerGeneratorList(){
		
	}

	@Override
	public List<Answer> generate(Question question) {
		List<Answer> answers = new ArrayList<Answer>();
		for(IAnswerGenerator gen : this){
			List<Answer> r = gen.generate(question);
			answers.addAll(r);
		}
		question.setAnswers(answers);
		return answers;
	}


}
