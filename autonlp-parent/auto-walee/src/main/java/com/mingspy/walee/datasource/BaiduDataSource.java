package com.mingspy.walee.datasource;

import java.util.ArrayList;
import java.util.List;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import com.mingspy.walee.core.Evidence;
import com.mingspy.walee.core.Query;

public class BaiduDataSource extends WebDataSource
{
    public static final String SOURCE = "baidu";
    public BaiduDataSource()
    {
        url = "http://www.baidu.com/s?tn=monline_5_dg&wd=";
    }

    @Override
    protected List<Evidence> parse(String content)
    {
        HtmlCleaner cleaner = new HtmlCleaner();
        TagNode root = cleaner.clean(content);
        List<Evidence> evids = new ArrayList<Evidence>();
        try {
            Object[] results = root
                               .evaluateXPath("//div[@id='content_left']//div[@class='result c-container']");

            for(Object r : results) {

                TagNode doc = (TagNode) r;
                Object [] title = doc.evaluateXPath("//h3[@class='t']//a");
                String t = ((TagNode)title[0]).getText().toString();
                Object [] abs = doc.evaluateXPath("//div[@class='c-abstract']");
                String cont = ((TagNode)abs[0]).getText().toString();
                Evidence evid = new Evidence();
                evid.setTitle(t);
                evid.setContent(cont);
                evid.setSource(SOURCE);
                evids.add(evid);
            }

            return evids;
        } catch (XPatherException e) {
            e.printStackTrace();
        }
        return null;
    }

}
