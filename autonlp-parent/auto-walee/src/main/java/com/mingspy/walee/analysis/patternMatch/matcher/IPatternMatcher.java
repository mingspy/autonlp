package com.mingspy.walee.analysis.patternMatch.matcher;

import com.mingspy.utils.ScoreList;
import com.mingspy.walee.analysis.patternMatch.types.Pattern;

public interface IPatternMatcher {
	ScoreList<Long> match(Pattern target);
	Pattern getPattern(long id);
}
