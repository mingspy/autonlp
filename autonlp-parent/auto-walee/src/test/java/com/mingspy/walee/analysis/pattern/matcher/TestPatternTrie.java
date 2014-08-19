package com.mingspy.walee.analysis.pattern.matcher;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.mingspy.utils.ScoreList;
import com.mingspy.walee.analysis.pattern.types.QuestionPattern;

public class TestPatternTrie {
	@Test
	public void match() {
		QuestionPattern p1 = new QuestionPattern(1, 10, "搜狐;汽车;自然语言处理;小组;",null);//
		QuestionPattern p2 = new QuestionPattern(2, 10, "搜狐;汽车;问答;小组;", null);//
		QuestionPattern p3 = new QuestionPattern(3, 10, "搜狐;*;小组;", null);//
		QuestionPattern p4 = new QuestionPattern(4, 10, "搜狐;测试;小组;", null);
		QuestionPattern p5 = new QuestionPattern(5, 10, "我;在;搜狐;汽车;问答;小组;工作;",null);
		QuestionPattern p6 = new QuestionPattern(6, 10, "我;在;汽车;问答;小组;工作;",null);
		QuestionPattern p7 = new QuestionPattern(7, 9, "技术;中心;", null);//
		QuestionPattern p8 = new QuestionPattern(8, 10, "我;在;汽车;技术;中心;小组;工作;",null);
		QuestionPattern p9 = new QuestionPattern(9, 1, "*", null);//
		QuestionPattern p10 = new QuestionPattern(10, 10, "搜狐;汽车", null);//
		
		PatternTrie trie = new PatternTrie();
		Assert.assertTrue(trie.add(p1));
		Assert.assertTrue(trie.add(p2));
		Assert.assertTrue(trie.add(p3));
		Assert.assertTrue(trie.add(p7));
		Assert.assertTrue(trie.add(p9));
		Assert.assertTrue(!trie.add(p1));
		Assert.assertTrue(trie.add(p10));
		
		ScoreList<Long> r = trie.match(p1);
		List<Long> shoud = new ArrayList<Long>();
		shoud.add(1L);shoud.add(3L);shoud.add(9L);shoud.add(10L);
		Assert.assertTrue(r.constainsAll(shoud));
		
		r = trie.match(p4);
		shoud.clear();shoud.add(3L);shoud.add(9L);
		Assert.assertTrue(r.constainsAll(shoud));
		
		r = trie.match(p5);
		shoud.clear();shoud.add(2L);shoud.add(3L);shoud.add(9L);shoud.add(10L);
		Assert.assertTrue(r.constainsAll(shoud));
		
		r = trie.match(p6);
		shoud.clear();shoud.add(9L);
		Assert.assertTrue(r.constainsAll(shoud));
		
		r = trie.match(p8);
		shoud.clear();shoud.add(9L);shoud.add(7L);
		Assert.assertTrue(r.constainsAll(shoud));
		
		System.out.println("query p1=>" + trie.match(p1));
		System.out.println("query p4=>" + trie.match(p4));
		System.out.println("query p5=>" + trie.match(p5));
		System.out.println("query p6=>" + trie.match(p6));
		System.out.println("query p8=>" + trie.match(p8));
	}
}
