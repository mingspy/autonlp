package com.mingspy.walee.core;

import java.util.List;

import com.mingspy.jseg.Token;


/**
 * 答案的证据。用于支持某答案的文本，问题等。
 * @author xiuleili
 *
 */
public class Evidence extends ScoreObj
{
    /**
     * 标题
     */
    protected String title;
    /**
     * 内容
     */
    protected String content;
    /**
     * 来源
     */
    protected String src;

    protected List<Token> titleTokens = null;
    protected List<Token> contentTokens = null;

    public List<Token> getTitleTokens()
    {
        return titleTokens;
    }

    public void setTitleTokens(List<Token> titleTokens)
    {
        this.titleTokens = titleTokens;
    }

    public List<Token> getContentTokens()
    {
        return contentTokens;
    }

    public void setContentTokens(List<Token> contentTokens)
    {
        this.contentTokens = contentTokens;
    }

    public void addScore(double s)
    {
        score += s;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("{title:\"");
        builder.append(title);
        builder.append("\",content:\"");
        builder.append(content);
        builder.append("\",score:");
        builder.append(score);
        builder.append(",src:\"");
        builder.append(src);
        builder.append("\"}");
        return builder.toString();
    }
    public String getContent()
    {
        return content;
    }
    public void setContent(String content)
    {
        this.content = content;
    }
    public String getSource()
    {
        return src;
    }
    public void setSource(String source)
    {
        this.src = source;
    }

    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
}
