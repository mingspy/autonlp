package com.mingspy.walee.answer;

import java.util.List;

import com.mingspy.walee.core.Answer;
import com.mingspy.walee.core.Question;

public interface IAnswerGenerator {
	/**
	 * 对问题产生答案，并进行答案评分。
	 * 答案产生方式:<br>
	 * 1. 从结构化的数据中查询获得.<br>
	 * 2. 从web上搜索产生.<br>
	 * 3. 从问答库中抽取问答对.<br>
	 * 4. 从文本库中抽取文章片段.<br>
	 * @param question
	 * @return
	 */
	List<Answer> generate(Question question);
	/*void softFiltering(Question question, List<Answer> answers);
	void searchEvidences(Question question, List<Answer> answers);
	void scoreEvidences(Question question, List<Answer> answers);
	void scoreAnswers(Question question, List<Answer> answers);*/

}
