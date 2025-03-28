package org.zerock.aop;

import lombok.extern.log4j.Log4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Log4j
@Component
public class LogAdvice {
//    @Before("execution(* org.zerock.service.SampleService*.*(..))")
//    public void logbefore() {
//        log.info("=======");
//    }
//    @After("execution(* org.zerock.service.SampleService*.*(..))")
//    public void logafter() {
//        log.info("=======");
//    }

    @Around("execution(* org.zerock.service.SampleService*.*(..))")
    public Object logTime(ProceedingJoinPoint pjp) {

        // Before
        log.info("시작 시간");
        long start = System.currentTimeMillis();

        Object result = null;

        try {
            result = pjp.proceed();
        }catch(Throwable e) {
            e.printStackTrace();
        }

        // After
        log.info("종료 시간");
        long end = System.currentTimeMillis();

        log.info("Time : " + (end - start));
        return result;
    }
}
