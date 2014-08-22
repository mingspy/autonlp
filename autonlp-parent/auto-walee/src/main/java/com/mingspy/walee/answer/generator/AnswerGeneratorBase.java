package com.mingspy.walee.answer.generator;
/*
import java.util.List;

import com.mingspy.walee.answer.IAnswerGenerator;
import com.mingspy.walee.answer.IAnswerScorer;
import com.mingspy.walee.answer.IEvidenceScorer;
import com.mingspy.walee.answer.IEvidenceSeacher;
import com.mingspy.walee.core.Answer;
import com.mingspy.walee.core.Evidence;
import com.mingspy.walee.core.Question;

public abstract class AnswerGeneratorBase implements IAnswerGenerator {

	protected IEvidenceSeacher evidenceSearcher = null;
	protected IEvidenceScorer evidenceScorer = null;
	protected IAnswerScorer answerScorer = null;
	@Override	
	public List<Answer> generate(Question question) {
		List<Answer> results = generateAnswer(question);
		if(evidenceSearcher != null){
			for(Answer answer : results){
				List<Evidence> evidences = evidenceSearcher.search(question, answer);
				answer.setEvidences(evidences);
				if(evidenceScorer != null){
					for (Evidence evidence : evidences){
						evidenceScorer.score(question, answer, evidence);
					}
				}
			}
		}
		
		if(answerScorer != null){
			for(Answer answer : results){
				answerScorer.score(question, answer);
			}
		}
		return results;
	}
	
	public IEvidenceSeacher getEvidenceSearcher() {
		return evidenceSearcher;
	}

	public void setEvidenceSearcher(IEvidenceSeacher evidenceSearcher) {
		this.evidenceSearcher = evidenceSearcher;
	}

	public IEvidenceScorer getEvidenceScorer() {
		return evidenceScorer;
	}

	public void setEvidenceScorer(IEvidenceScorer evidenceScorer) {
		this.evidenceScorer = evidenceScorer;
	}

	public IAnswerScorer getAnswerScorer() {
		return answerScorer;
	}

	public void setAnswerScorer(IAnswerScorer answerScorer) {
		this.answerScorer = answerScorer;
	}

	protected abstract List<Answer> generateAnswer(Question question);

}
*/