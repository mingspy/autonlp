package com.mingspy.walee.analysis;

import java.util.List;

import org.apache.log4j.Logger;

import com.mingspy.jseg.Token;
import com.mingspy.walee.core.Question;
import com.mingspy.walee.core.Tools;

/**
 * 分词器：完成分词和词性标注功能。
 * @author xiuleili
 *
 */
public class TokenAnalyzer implements IQAnalyzer
{

    private static final Logger LOG = Logger.getLogger(TokenAnalyzer.class);

    @Override
    public boolean analysis(Question question)
    {
        String content = question.getContent();
        List<Token> tokens = Tools.POSTagging(content);
        if(tokens != null) {
            question.setProperty(Question.TOKENS, tokens);
            LOG.debug("问题分词结果:["+tokens+"]");
            return true;
        }

        return false;
    }

}
