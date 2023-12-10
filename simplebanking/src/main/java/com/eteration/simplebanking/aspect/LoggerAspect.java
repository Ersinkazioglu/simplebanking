package com.eteration.simplebanking.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAspect {

    private Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    @Before("execution(* com.eteration.simplebanking.controller.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Before executing: " + joinPoint.getSignature().toShortString());

    }

    @After("execution(* com.eteration.simplebanking.controller..*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("After executing: " + joinPoint.getSignature().toShortString() );
    }
}