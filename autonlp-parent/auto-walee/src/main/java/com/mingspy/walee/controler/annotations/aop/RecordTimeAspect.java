package com.mingspy.walee.controler.annotations.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class RecordTimeAspect
{

    private final static Logger LOG = Logger.getLogger(RecordTimeAspect.class);
    private final static String RECORD_TIME = "@annotation(com.mingspy.utils.annotations.RecordTime)";

    @Around(RECORD_TIME)
    public Object aroundRecordTime(ProceedingJoinPoint p) throws Throwable
    {
        System.out.println("**********************");
        Object result = null;
        long start_time = System.currentTimeMillis();
        result = p.proceed();
        long end_time = System.currentTimeMillis();
        LOG.info("run " + p.getSignature().getName() + " used: " + (end_time - start_time));
        System.out.println("...aroundRecordTime....");
        return result;
    }
}
