package com.mingspy.walee.answer;

import com.mingspy.walee.core.Question;

/**
 * 答案合成器，用于合并答案，对答案进行最后的综合选择以及必要的合成。
 * @author xiuleili
 *
 */
public interface IAnswerSynthesizer {
	void synthesis(Question question);
}
