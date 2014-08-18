package com.mingspy.walee;

import com.mingspy.walee.core.Question;

public interface IChatting {
	
	/**
	 * 回答用户问题。
	 * @param question 待回答的用户问题。
	 * @return 问题以及问题答案。
	 */
	Question answer(String question);
}
