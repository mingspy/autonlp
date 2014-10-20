/**
 *
 * APDPlat - Application Product Development Platform
 * Copyright (c) 2013, 杨尚川, yang-shangchuan@qq.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.mingspy.walee.answer.scorer.answer;

import java.util.List;

import org.apache.log4j.Logger;

import com.mingspy.jseg.Token;
import com.mingspy.walee.answer.IAnswerScorer;
import com.mingspy.walee.core.Answer;
import com.mingspy.walee.core.Configuration;
import com.mingspy.walee.core.Evidence;
import com.mingspy.walee.core.Question;

/**
 * 对候选答案进行评分 【词频评分组件】 title中出现一次算TITLE_WEIGHT次 snippet中出现一次算1次 候选答案的分值 += 词频 *
 * 权重
 *
 * @author xiuleili
 */
public class TermFrequencyAnswerScorer implements IAnswerScorer
{

    public static final String WEIGHT_TERMFRESCORER = "WEIGHT_TERMFRE_ANSWERSCORER";
    private static final Logger LOG = Logger
                                      .getLogger(TermFrequencyAnswerScorer.class);
    private static final int TITLE_WEIGHT = 2;

    @Override
    public double score(Question question, Answer answer,
                        List<Evidence> evidences)
    {
        LOG.debug("---------------------------");
        LOG.debug("词频评分开始："+answer);
        double count = 0;
        double sum = 0.1;
        String answerStr = answer.getContent();
        for(Evidence ev : evidences) {
            for(Token t : ev.getTitleTokens()) {
                if(t.word.equalsIgnoreCase(answerStr)) {
                    count += TITLE_WEIGHT * ev.getScore();
                }
            }

            for(Token t : ev.getContentTokens()) {
                if(t.word.equalsIgnoreCase(answerStr)) {
                    count += ev.getScore();
                }
            }

            sum += (ev.getTitleTokens().size() + ev.getContentTokens().size()) * ev.getScore();
        }
        double score = count/(sum) * Configuration.instance().getDouble(WEIGHT_TERMFRESCORER, 1.0);

        LOG.debug("词频:"+count+"得分:"+score);
        LOG.debug("词频评分结束");
        LOG.debug("---------------------------");
        return score;
    }
}
