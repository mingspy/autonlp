package com.mingspy.walee.core;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class TestCategory
{

    @Test
    public void parse()
    {
        String cStr1 = "/1/2/3:0.5,/12/3:0.3,";
        String cStr2 = "/1/2/3:0.5,/12/3:0.3";
        String cStr3 = ",/1/2/3:0.5,/12/3:0.3";
        String cStr4 = ",/1/2/3:0.5,/12/3:0.3,";

        List<Category> c1 = Category.parse(cStr1);
        List<Category> c2 = Category.parse(cStr2);
        List<Category> c3 = Category.parse(cStr3);
        List<Category> c4 = Category.parse(cStr4);

        Assert.assertTrue(c1.equals(c2));
        Assert.assertTrue(c1.equals(c3));
        Assert.assertTrue(c1.equals(c4));
    }

    @Test
    public void toStr()
    {
        String cStr1 = "/1/2/3:0.5,/12/3:0.3,";
        String cStr2 = "/1/2/3:0.5,/12/3:0.3";

        List<Category> c1 = Category.parse(cStr1);
        List<Category> c2 = Category.parse(cStr2);

        String cs1 = Category.toString(c1);
        String cs2 = Category.toString(c2);

        Assert.assertEquals(cs1, cs2);

    }

    @Test
    public void distance()
    {
        String [] ss = "/1/2/3//".split("/");
        System.out.println(ss.length);
        for(String s:ss) {
            System.out.println(s);
        }
        Assert.assertTrue(new Category("/1/2/3/4/5/6/7", 1.0)
                          .distance(new Category("/1/2/3/6/7/8/9", 1.0)) == 8);
        Assert.assertTrue(new Category("/2/2/3/4/5/6/7", 1.0)
                          .distance(new Category("/1/2/3/4/5/6/7", 1.0)) == 14);
        Assert.assertTrue(new Category("/1/2/3/4/5/6/7", 1.0)
                          .distance(new Category("/1/2/3/4/5/6/7", 1.0)) == 0);
        Assert.assertTrue(new Category("/4", 1.0).distance(new Category(
                              "/1/2/3/4/5/6/7", 1.0)) == 8);
    }
}
