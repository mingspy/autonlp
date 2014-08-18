package com.mingspy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mingspy.walee.controler.annotations.RecordTime;
import com.mingspy.walee.controler.annotations.aop.TestAop;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:aopContext.xml" })
public class TestMSTimerAnnotation {

	
	@RecordTime
	public void sleep(){
		try {
			System.out.println("going to sleep..");
			Thread.currentThread().sleep(1000);
			System.out.println("wake up..");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Autowired
	TestAop tBean;
	
	@Test
	public void sleepOnesec(){
		sleep();
		///tt.go();
		System.out.println("new ......");
		TestAop tNew = new TestAop();
		tNew.testRecordTime();
		System.out.println("tBean ......");
		tBean.testRecordTime();
	}
}
