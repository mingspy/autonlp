package com.mingspy.walee.analysis;

import java.util.List;

import org.apache.log4j.Logger;

import com.mingspy.jseg.CppTokenizer;
import com.mingspy.jseg.ITokenizer;
import com.mingspy.jseg.Token;
import com.mingspy.walee.core.Question;

public class Tokenizer implements IQAnalyzer {

	private static final Logger LOG = Logger.getLogger(Tokenizer.class);
	private ITokenizer _tokenizer = new CppTokenizer();
	@Override
	public boolean analysis(Question question) {
		String content = question.getContent();
		List<Token> tokens = _tokenizer.POSTagging(content);
		if(tokens != null){
			question.setProperty(Question.TOKENS, tokens);
			LOG.debug("问题分词结果:["+tokens+"]");	
			return true;
		}
		
		return false;
	}

}
