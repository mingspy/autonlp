package com.mingspy.walee.analysis.pattern.matcher;

import com.mingspy.utils.ScoreList;
import com.mingspy.walee.analysis.pattern.types.Pattern;

public interface IPatternMatcher
{
    ScoreList<Long> match(Pattern target);
}
