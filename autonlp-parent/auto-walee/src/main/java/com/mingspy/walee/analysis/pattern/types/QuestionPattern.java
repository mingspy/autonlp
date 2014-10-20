package com.mingspy.walee.analysis.pattern.types;

import java.util.LinkedList;
import java.util.List;

import com.mingspy.jseg.Token;


public class QuestionPattern extends Pattern
{

    private static final long serialVersionUID = -7070810326833496263L;
    private QuestionPattern parrent;
    private List<QuestionPattern> childs;

    public QuestionPattern(long id, int score, String patternstr, String topic)
    {
        super(id, score, patternstr, topic);
    }

    public QuestionPattern(long id, int score, List<Item> items, String topic)
    {
        super(id, score, items, topic);
    }

    public QuestionPattern getParrent()
    {
        return parrent;
    }

    public void setParrent(QuestionPattern parrent)
    {
        this.parrent = parrent;
    }

    public List<QuestionPattern> getChilds()
    {
        if(childs == null) {
            childs = new LinkedList<QuestionPattern>();
        }
        return childs;
    }

    public void setChilds(List<QuestionPattern> childs)
    {
        this.childs = childs;
    }

    public static QuestionPattern toPattern(List<Token> tokens)
    {
        if(tokens == null) {
            return null;
        }

        List<Item> items = new LinkedList<Item>();
        QuestionPattern pattern = new QuestionPattern(-1, -1, items, null);
        for(Token t:tokens) {
            String cat = t.nature;

            if( cat != null) {
                pattern.addItem(cat);
            } else {
                pattern.addItem(t.nature);
            }
        }

        return pattern;
    }

    @Override
    public QuestionPattern clone()
    {
        return new QuestionPattern(this.patternId, this.score, items, category);
    }


}
