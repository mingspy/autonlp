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

import java.util.List;

import org.apache.log4j.Logger;

import com.mingspy.jseg.Token;
import com.mingspy.walee.answer.IEvidenceScorer;
import com.mingspy.walee.core.Configuration;
import com.mingspy.walee.core.Evidence;
import com.mingspy.walee.core.Question;
import com.mingspy.walee.core.Tools;

/**
 * 对证据进行评分 【TermMatch评分组件】 不管语法关系或词序，直接对问题和证据的词进行匹配 对于问题中的词，在title中出现一次记2/idf分
 * 对于问题中的词，在snippet中出现一次记1/idf分
 *
 * @author xiuleili
 */
public class TermMatchEvidenceScorer implements IEvidenceScorer
{

    private static final Logger LOG = Logger.getLogger(TermMatchEvidenceScorer.class);

    private static final String WEIGHT_TERMSCORER = "WEIGHT_TERMSCORER";
    @Override
    public double score(Question question, Evidence evidence)
    {
        LOG.debug("*************************");
        LOG.debug("Evidence TermMatch评分开始");

        List<Token> qTokens = (List<Token>) question
                              .getProperty(Question.TOKENS);

        //不管语法关系或词序，直接对问题和证据的词进行匹配
        //对于问题中的词，在evidence中出现一次记一分
        double score = 0;
        double total_idf = 0;
        for (Token qToken : qTokens) {
            //忽略问题中长度为1的词, 忽略标点符号。
            if (qToken.word.length() < 2 || qToken.nature.equalsIgnoreCase("w")) {
                LOG.debug("忽略问题中长度为1的词:" + qToken);
                continue;
            }
            double idf = Tools.getIDF(qToken.word);
            total_idf += idf;
            for (Token tToken : evidence.getTitleTokens()) {
                if (qToken.word.equalsIgnoreCase(tToken.word)) {
                    score += idf * 2;
                }
            }

            for (Token eToken : evidence.getContentTokens()) {
                if (qToken.word.equalsIgnoreCase(eToken.word)) {
                    score += idf * 2;
                }
            }

        }

        total_idf += Tools.totalIDF(evidence.getTitleTokens()) * 2;
        total_idf += Tools.totalIDF(evidence.getContentTokens());
        //  归一化得分
        score *=  Configuration.instance().getDouble(WEIGHT_TERMSCORER, 1.0)/(total_idf + 0.00001);

        LOG.debug("Evidence TermMatch评分:" + score);
        LOG.debug("Evidence TermMatch评分结束");
        LOG.debug("*************************");
        return score;
    }


}