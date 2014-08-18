package com.mingspy.walee.answer;

import java.util.List;

import com.mingspy.walee.core.Answer;
import com.mingspy.walee.core.Evidence;
import com.mingspy.walee.core.Question;

public interface IEvidenceSeacher {
	/**
	 * 搜索支持答案的证据。
	 * @param question
	 * @param answer
	 * @return
	 */
	List<Evidence> search(Question question, Answer answer);
}
