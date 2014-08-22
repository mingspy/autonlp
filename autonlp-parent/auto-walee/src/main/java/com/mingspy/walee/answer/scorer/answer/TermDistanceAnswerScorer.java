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

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mingspy.jseg.Token;
import com.mingspy.walee.answer.IAnswerScorer;
import com.mingspy.walee.core.Answer;
import com.mingspy.walee.core.Evidence;
import com.mingspy.walee.core.Question;

/**
 * 对候选答案进行评分 【词距评分组件】 分值+=原分值*（1/词距）
 * 
 * @author xiuleili
 */
public class TermDistanceAnswerScorer implements IAnswerScorer {

	private static final Logger LOG = Logger
			.getLogger(TermDistanceAnswerScorer.class);

	@Override
	public double score(Question question, Answer answer,
			List<Evidence> evidences) {
		LOG.debug("*************************");
		LOG.debug("词距评分开始");
		// 1、对问题进行分词
		List<Token> qTokens = (List<Token>) question
				.getProperty(Question.TOKENS);
		// 2、对证据进行分词
		double score = 0;
		double sum = 0.1;
		String answerStr = answer.getContent();
		LOG.debug("计算候选答案 " + answerStr + " 的词距");
		for (Evidence ev : evidences) {
			// 3、计算候选答案的词距
			int distance = 0;

			// 3.1 计算candidateAnswer的分布
			List<Integer> candidateAnswerOffes = new ArrayList<Integer>();
			// 3.2 计算questionTerm的分布
			List<Integer> questionTermOffes = new ArrayList<Integer>();
			for (Token term : ev.getTitleTokens()) {
				if (term.word.equalsIgnoreCase(answerStr)) {
					candidateAnswerOffes.add(term.off);
				}

				for (Token token : qTokens) {
					if (term.word.equalsIgnoreCase(token.word)) {
						questionTermOffes.add(term.off);
					}
				}
			}

			for (Token term : ev.getContentTokens()) {
				if (term.word.equalsIgnoreCase(answerStr)) {
					candidateAnswerOffes.add(term.off);
				}

				for (Token token : qTokens) {
					if (term.word.equalsIgnoreCase(token.word)) {
						questionTermOffes.add(term.off);
					}
				}
			}

			// 3.3 计算candidateAnswer和questionTerm的词距
			for (int candidateAnswerOffe : candidateAnswerOffes) {
				for (int questionTermOffe : questionTermOffes) {
					distance += Math
							.abs(candidateAnswerOffe - questionTermOffe);
				}
			}
			if(distance > 0){
				score += ev.getScore()/ (distance+ev.getTitleTokens().size() + ev.getContentTokens().size() + 0.1);
			}
			
			sum += ev.getScore();
		}

		score /= sum;

		LOG.debug("词距评分结束，得分:"+score);
		LOG.debug("*************************");

		return score;
	}
}