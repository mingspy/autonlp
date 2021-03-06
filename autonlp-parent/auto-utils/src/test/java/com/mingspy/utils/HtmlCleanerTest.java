package com.mingspy.utils;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.junit.Assert;
import org.junit.Test;

public class HtmlCleanerTest
{

    @Test
    public void testCleaner() throws XPatherException
    {
        HtmlCleaner cleaner = new HtmlCleaner();
        TagNode node = cleaner.clean("<boy><p s=1>1st p node</p><p s=2>2nd p node</p></body>");
        TagNode[] list = node.getElementsByName("p", true);
        for(TagNode n : list) {
            System.out.println(n.getName()+"=>"+n.getText());
        }

        Object [] ps = node.evaluateXPath("//p[@s=1]");
        Assert.assertNotNull(ps);
        for(int i = 0; i<ps.length; i++) {
            System.out.println(((TagNode)ps[i]).getText());
        }
    }

    @Test
    public void testP()
    {
        HtmlCleaner cleaner = new HtmlCleaner();
        TagNode node = cleaner.clean("<p class=\"name\">董建刚<a href=\"http://www.bitauto.com/feedback/FAQ.aspx?col=5&tab=8\" target=\"_blank\"><em class=\"ask_qczj ask_img\"></em></a></p>");
        String content = node.getText().toString();
        System.out.println(content);
    }
}
