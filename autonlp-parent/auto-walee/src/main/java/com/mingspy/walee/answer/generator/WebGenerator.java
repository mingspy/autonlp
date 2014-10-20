package com.mingspy.walee.answer.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.mingspy.jseg.Token;
import com.mingspy.walee.answer.IAnswerGenerator;
import com.mingspy.walee.answer.IEvidenceSeacher;
import com.mingspy.walee.answer.scorer.answer.AnswerScorerList;
import com.mingspy.walee.answer.scorer.answer.TermDistanceAnswerScorer;
import com.mingspy.walee.answer.scorer.answer.TermFrequencyAnswerScorer;
import com.mingspy.walee.answer.scorer.answer.TermMinDistanceAnswerScorer;
import com.mingspy.walee.answer.scorer.evidence.BigramEvidenceScorer;
import com.mingspy.walee.answer.scorer.evidence.CSEvidenceScorer;
import com.mingspy.walee.answer.scorer.evidence.EvidenceScorerList;
import com.mingspy.walee.answer.scorer.evidence.SkipBigramEvidenceScore;
import com.mingspy.walee.answer.scorer.evidence.TermMatchEvidenceScorer;
import com.mingspy.walee.core.Answer;
import com.mingspy.walee.core.Evidence;
import com.mingspy.walee.core.Query;
import com.mingspy.walee.core.Question;
import com.mingspy.walee.core.Tools;
import com.mingspy.walee.datasource.BaiduDataSource;
import com.mingspy.walee.datasource.IDataSource;

/**
 * 从web上搜索数据，产生答案。
 *
 * @author xiuleili
 *
 */
public class WebGenerator implements IAnswerGenerator
{
    private static final Logger LOG = Logger.getLogger(WebGenerator.class);

    protected IEvidenceSeacher evidenceSearcher = null;
    protected EvidenceScorerList evidenceScorer = new EvidenceScorerList();
    protected AnswerScorerList answerScorer = new AnswerScorerList();

    private IDataSource baiduDataSource = new BaiduDataSource();
    public WebGenerator()
    {
        evidenceScorer.add(new TermMatchEvidenceScorer());
        evidenceScorer.add(new BigramEvidenceScorer());
        evidenceScorer.add(new SkipBigramEvidenceScore());
        evidenceScorer.add(new CSEvidenceScorer());

        // answer scorer.
        answerScorer.add(new TermFrequencyAnswerScorer());
        answerScorer.add(new TermDistanceAnswerScorer());
        answerScorer.add(new TermMinDistanceAnswerScorer());
    }
    @Override
    public List<Answer> generate(Question question)
    {
        // 1. 搜索相关内容
        LOG.debug("从web上获取相关内容");
        Query query = new Query(question.getContent());
        List<Evidence> evidences = baiduDataSource.find(query);
        // 2. 产生答案
        if(evidences == null) return null;
        List<Answer> answers = new ArrayList<Answer>();
        findAnswerWithSameLAT(question, evidences, answers);
        LOG.debug("产生的备选答案："+answers);
        // 3. 证据评分
        for(Evidence evid : evidences) {
            double score = evidenceScorer.score(question, evid);
            evid.addScore(score);
        }

        Collections.sort(evidences, Tools.DSC_SCORE_COMPARAROTR);
        //LOG.info("最佳证据:"+evidences.get(0));

        // 4. 答案评分
        for(Answer answer : answers) {
            double score = answerScorer.score(question, answer, evidences);
            answer.addScore(score);
            answer.setEvidences(evidences);
        }

        Collections.sort(answers, Tools.DSC_SCORE_COMPARAROTR);
        LOG.debug("最终答案："+answers);
        return answers;
    }

    private boolean findAnswerWithSameLAT(Question question, List<Evidence> evidences, List<Answer> results)
    {
        // 1. 找到类型与答案类型一致的答案。
        String lat = (String)question.getProperty(Question.ANSWER_TYPE);
        Set<String> answers = new HashSet<String>();
        for(Evidence evid : evidences) {
            evid.setTitleTokens(Tools.POSTagging(evid.getTitle()));
            evid.setContentTokens(Tools.POSTagging(evid.getContent()));
            List<Token> tokens = new LinkedList<Token>();
            tokens.addAll(evid.getTitleTokens());
            tokens.addAll(evid.getContentTokens());
            for(Token t : tokens) {
                if(t.nature.equalsIgnoreCase(lat) || t.nature.startsWith(lat)) {
                    if(!t.word.trim().isEmpty())
                        answers.add(t.word);
                }
            }
        }

        // 2. 过滤在问题中出现的词
        List<Token> tokens = (List<Token>)question.getProperty(Question.TOKENS);
        List<String> toRemove = new ArrayList<String>();
        for(String answer : answers) {
            for(Token t : tokens) {
                if(answer.equalsIgnoreCase(t.word)) {
                    toRemove.add(answer);
                }
            }
        }

        answers.removeAll(toRemove);
        for(String answer : answers) {
            results.add(new Answer(answer));
        }
        return false;
    }

}
