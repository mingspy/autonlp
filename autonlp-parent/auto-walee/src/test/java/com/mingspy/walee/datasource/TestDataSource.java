package com.mingspy.walee.datasource;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.mingspy.walee.core.Evidence;
import com.mingspy.walee.core.Query;

public class TestDataSource {
	@Test
	public void baidu(){
		BaiduDataSource b = new BaiduDataSource();
		List<Evidence> r = b.find(new Query("机器学习"));
		Assert.assertTrue(r.size() > 0);
	}

}
