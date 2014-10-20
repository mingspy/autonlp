package com.mingspy.walee.answer.generator;

import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.mingspy.jseg.Token;
import com.mingspy.walee.core.Answer;
import com.mingspy.walee.core.Question;
import com.mingspy.walee.core.Tools;

public class TestWebGenerator
{
    //@Ignore
    @Test
    public void longestRiver()
    {
        testAnswer("世界上最长的河是?", "尼罗河", "ns");
    }

    //@Ignore
    @Test
    public void birthDay()
    {
        // 日期识别有bug
        //testAnswer("毛泽东的生日是?", "1893年12月26日", "t");
        testAnswer("李连杰的身高是多少?", "1893年12月26日", "m");
        //testAnswer("李洪志在哪里?", "1893年12月26日", "nt");
    }

    //@Ignore
    @Test
    public void person()
    {
        testAnswer("2014年华人首富是谁?", "李嘉诚", "nr");
    }

    //@Ignore
    @Test
    public void place()
    {
        // 组织结构识别有bug
        testAnswer("哪家银行的简称是HSBC?", "汇丰银行", "nt");
    }

    //@Ignore
    @Test
    public void location()
    {
        //String questionStr = "被誉为“风车之国”是哪个国家?"; // 荷兰
        testAnswer("第一套奥林匹克邮票是哪个国家发行的?", "希腊", "ns");
    }

    //@Ignore
    @Test
    public void price()
    {
        // 金钱，价格识别有bug
        System.out.println(Tools.POSTagging("市场销售价:25.19万元起"));
        testAnswer("宝马多少钱", "20", "price");
    }

    private void testAnswer(String question, String answer, String ty)
    {
        Question q = new Question(question);
        List<Token> tokens = Tools.POSTagging(question);
        q.setProperty(Question.TOKENS, tokens);
        q.setProperty(Question.ANSWER_TYPE, ty);
        WebGenerator gen = new WebGenerator();
        List<Answer> answers = gen.generate(q);
        System.out.println("问题："+question+" 答案： "+answers.get(0));
        Assert.assertTrue(answers.get(0).getContent().equals(answer));
    }
}
