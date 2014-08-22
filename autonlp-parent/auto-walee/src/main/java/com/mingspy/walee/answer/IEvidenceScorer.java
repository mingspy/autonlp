package com.mingspy.walee.answer;

import com.mingspy.walee.core.Answer;
import com.mingspy.walee.core.Evidence;
import com.mingspy.walee.core.Question;

public interface IEvidenceScorer {
	/**
	 * 对证据进行评分。
	 * @param question
	 * @param evidence
	 * @return
	 */
	double score(Question question, Evidence evidence);
}
