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

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mingspy.walee.answer.IAnswerScorer;
import com.mingspy.walee.core.Answer;
import com.mingspy.walee.core.Evidence;
import com.mingspy.walee.core.Question;

/**
 * 组合候选答案评分组件
 *
 * @author xiuleili
 */
public class AnswerScorerList extends LinkedList<IAnswerScorer> implements IAnswerScorer {
	public AnswerScorerList(){
		
	}

    private static final Logger LOG = LoggerFactory.getLogger(AnswerScorerList.class);
	@Override
	public double score(Question question, Answer answer,
			List<Evidence> evidences) {
		double score = 0;
		for (IAnswerScorer scorer : this) {
			score += scorer.score(question, answer, evidences);
        }
		return score/(this.size() + 0.1);
	}

}

