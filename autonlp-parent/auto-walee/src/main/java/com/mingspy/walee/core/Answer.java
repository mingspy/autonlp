package com.mingspy.walee.core;

import java.util.List;

/**
 * 问题的答案。
 *
 *
 * @author xiuleili
 *
 */
public class Answer extends ScoreObj
{
    /**
     * 答案的内容
     */
    private String content;

    /**
     * 答案的证据列表。支持获得该答案的所有引用的文本，资源。
     */
    private List<Evidence> evidences;

    public Answer() {}

    public Answer(String answer)
    {
        content = answer;
    }

    @Override
    public String toString()
    {
        return "content:"+content+", score:"+score;
    }

    @Override
    public boolean equals(Object obj)
    {
        String anotherContent = null;
        if(obj instanceof Answer) {
            anotherContent = ((Answer)obj).content;
        } else {
            anotherContent = obj.toString();
        }

        if(content != null && anotherContent != null) {
            return content.equalsIgnoreCase(anotherContent);
        }

        return false;
    }
    public String getContent()
    {
        return content;
    }
    public void setContent(String content)
    {
        this.content = content;
    }

    public List<Evidence> getEvidences()
    {
        return evidences;
    }
    public void setEvidences(List<Evidence> evidences)
    {
        this.evidences = evidences;
    }

    public void addEvidences(List<Evidence> evidences)
    {
        if(this.evidences == null) {
            this.evidences = evidences;
        } else {
            this.evidences.addAll(evidences);
        }
    }

}
