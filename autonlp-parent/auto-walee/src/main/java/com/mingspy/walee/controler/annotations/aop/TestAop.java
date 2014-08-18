package com.mingspy.walee.controler.annotations.aop;

import com.mingspy.walee.controler.annotations.RecordTime;

public class TestAop {

	@RecordTime
	public void testRecordTime() {
		System.out.println("testRecordTime() is running.");
		try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("testRecordTime() is quiting.");
	}
}
