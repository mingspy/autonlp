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

package com.mingspy.walee.answer.scorer.evidence;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mingspy.jseg.Token;
import com.mingspy.walee.answer.IEvidenceScorer;
import com.mingspy.walee.core.Configuration;
import com.mingspy.walee.core.Evidence;
import com.mingspy.walee.core.Question;

/**
 * 对证据进行评分 【跳跃二元模型评分组件】 利用跳跃二元模型构造出问题的所有正则表达式 在证据中进行匹配，匹配1次得2分
 *
 * @author 杨尚川
 */
public class SkipBigramEvidenceScore implements IEvidenceScorer
{

    private static final Logger LOG = Logger
                                      .getLogger(SkipBigramEvidenceScore.class);
    private static final String WEIGHT_SKIPBISCORER = "WEIGHT_SKIPBISCORER";

    @Override
    public double score(Question question, Evidence evidence)
    {
        LOG.debug("*************************");
        LOG.debug("Evidence 跳跃二元模型评分开始");
        List<Token> qTokens = (List<Token>) question
                              .getProperty(Question.TOKENS);
        List<Token> evidTokens = new LinkedList<Token>();
        evidTokens.addAll(evidence.getTitleTokens());
        evidTokens.addAll(evidence.getContentTokens());

        // 归一化得分
        int count = countSkipBigram(qTokens, evidTokens);
        double score = count / (qTokens.size() + evidTokens.size() - count + 0.1)
                       * Configuration.instance().getDouble(WEIGHT_SKIPBISCORER, 2.0);
        LOG.debug("Evidence 跳跃二元模型评分:" + score);
        LOG.debug("Evidence 跳跃二元模型评分结束");
        LOG.debug("*************************");
        return score;
    }

    public static int countSkipBigram(List<Token> qTokens, List<Token> eTokens)
    {
        int count = 0;
        for (int i = 0; i < qTokens.size() - 2; i++) {
            Token qToken = qTokens.get(i);
            // 忽略标点符号
            if (qToken.nature.equalsIgnoreCase("w")) {
                continue;
            }

            Token qTokenNext = qTokens.get(i + 2);
            for (int j = 0; j < eTokens.size() - 2; j++) {
                if (qToken.word.equalsIgnoreCase(eTokens.get(j).word)
                        && qTokenNext.word
                        .equalsIgnoreCase(eTokens.get(j + 2).word)) {
                    count++;
                }
            }
        }

        return count;
    }
}