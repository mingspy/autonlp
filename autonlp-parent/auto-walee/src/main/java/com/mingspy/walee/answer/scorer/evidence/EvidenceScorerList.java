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

import org.apache.log4j.Logger;

import com.mingspy.walee.answer.IEvidenceScorer;
import com.mingspy.walee.core.Evidence;
import com.mingspy.walee.core.Question;

/**
 * 组合证据评分组件
 *
 * @author xiuleili
 */
public class EvidenceScorerList extends LinkedList<IEvidenceScorer> implements IEvidenceScorer {

    //private final List<IEvidenceScorer> evidenceScores = new ArrayList<IEvidenceScorer>();
	private static final Logger LOG = Logger.getLogger(EvidenceScorerList.class);
    @Override
    public double score(Question question, Evidence evidence) {
    	LOG.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++");
    	LOG.debug("开始对证据评分:"+evidence);
    	double score = 0;
        for (IEvidenceScorer evidenceScore : this) {
        	score += evidenceScore.score(question, evidence);
        }
        score = score / (this.size() + 0.00000000001);
        LOG.debug("证据最终得分:"+score);
        LOG.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++");
        return score;
    }

}