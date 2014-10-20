package com.mingspy.walee.analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mingspy.jseg.Token;
import com.mingspy.utils.ScoreList;
import com.mingspy.walee.analysis.pattern.matcher.IPatternMatcher;
import com.mingspy.walee.analysis.pattern.matcher.PatternTrie;
import com.mingspy.walee.analysis.pattern.types.Item;
import com.mingspy.walee.analysis.pattern.types.Pattern;
import com.mingspy.walee.core.Category;
import com.mingspy.walee.core.Question;
import com.mingspy.walee.core.Slot;
import com.mingspy.walee.core.Tools;

/**
 * 问题分类器
 *
 * @depends NameEntityAnalyzer已对问题进行命名实体识别
 * @author xiuleili
 *
 */
public class CategoryAnalyzer implements IQAnalyzer
{
    private static final Logger LOG = Logger.getLogger(CategoryAnalyzer.class);

    private IPatternMatcher matcher = new PatternTrie();
    private Map<Long, Pattern> patternMap = new HashMap<Long, Pattern>();

    public CategoryAnalyzer()
    {
        loadPatterns();
    }

    private void loadPatterns()
    {
        // TODO Auto-generated method stub
        throw new RuntimeException("has not implement");
    }

    @Override
    public boolean analysis(Question question)
    {
        LOG.debug("识别问题分类:[" + question.getContent() + "]");

        // 1. 使用原字符串进行模板匹配
        Pattern p1 = genWordPattern(question);
        ScoreList<Long> matched1 = matcher.match(p1);

        // 2. 使用命名实体进行字符串匹配
        if (matched1 == null) {
            LOG.debug("使用原问题pattern匹配失败。尝试命名实体pattern");
            Pattern p2 = genNameEntityPattern(question);
            matched1 = matcher.match(p2);
        }

        if (matched1 == null) {
            LOG.debug("使用问题命名实体匹配失败");
            return false;
        }

        // 3. 按重要程度添加分类统计
        List<Category> cats = new ArrayList<Category>();
        for (Map.Entry<Long, Double> en : matched1.getEntries()) {
            Pattern p = patternMap.get(en.getKey());
            List<Category> pattern_cats = p.getCategories();
            for (Category pcat : pattern_cats) {
                Category cat = null;
                for (Category c : cats) {
                    if (c.equals(pcat)) {
                        cat = c;
                        break;
                    }
                }

                if (cat == null) {
                    cat = new Category(pcat.getName(), 0);
                    cats.add(cat);
                }

                cat.addScore( en.getValue()
                              * pcat.getScore());
            }
        }

        // 4. 分类排序
        Collections.sort(cats, Tools.DSC_SCORE_COMPARAROTR);

        // 5. 归一化分类
        double sum = 0.000005;
        for (Category c : cats) {
            sum += c.getScore();
        }
        for (Category c : cats) {
            c.setScore(c.getScore() / sum);
        }

        // 设置问题分类
        LOG.debug("识别的问题分类:" + cats);
        question.setProperty(Question.CATEGORY, cats);
        return true;
    }

    /**
     * 使用原字符串产生pattern。
     *
     * @param question
     * @return
     */
    private Pattern genWordPattern(Question question)
    {
        List<Token> tokens = (List<Token>) question
                             .getProperty(Question.TOKENS);
        List<Item> items = new ArrayList<Item>();
        for (Token t : tokens) {
            items.add(new Item(t.word));
        }
        return new Pattern(-1, 0, items, null);
    }

    /**
     * 使用命名实体产生pattern
     *
     * @param question
     * @return
     */
    private Pattern genNameEntityPattern(Question question)
    {
        List<Token> tokens = (List<Token>) question
                             .getProperty(Question.TOKENS);
        List<Item> items = new ArrayList<Item>();
        for (Token t : tokens) {
            if (Slot.isSlot(t.nature)) {
                items.add(new Item(t.nature));
            } else {
                items.add(new Item(t.word));
            }
        }
        return new Pattern(-1, 0, items, null);
    }

}
