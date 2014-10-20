package com.mingspy.walee.answer;

import java.util.List;

import com.mingspy.walee.core.Answer;
import com.mingspy.walee.core.Evidence;
import com.mingspy.walee.core.Question;

public interface IAnswerScorer
{
    /**
     * 对答案进行打分
     * @param question
     * @param answer
     * @return
     */
    double score(Question question, Answer answer, List<Evidence> evidences);
}
