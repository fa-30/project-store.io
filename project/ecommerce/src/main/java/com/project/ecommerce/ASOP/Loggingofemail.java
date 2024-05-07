package com.project.ecommerce.ASOP;

import org.apache.catalina.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("unused")
@Aspect
@Component
@Slf4j

public class Loggingofemail {
    @Pointcut("execution(* com.project.ecommerce.api.controller.EmailSendController..*(..))")
    public void loggingPointCutEmailController() {}

    @Before("loggingPointCutEmailController()")
    public void beforeEmailControllerMethods(JoinPoint joinPoint) {
        log.info("Before executing method in EmailController: {}", joinPoint.getSignature().getName());
    }
    @Around("loggingPointCutEmailController()")
    public Object monitorPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        log.info("PerformanceMonitoringAspect: Method " + joinPoint.getSignature() +
                " executed in " + executionTime + " milliseconds");
        return result;
    }
    @After("loggingPointCutEmailController()")
    public void afterEmailControllerMethods(JoinPoint joinPoint) {
        log.info("After executing method in EmailController: {}", joinPoint.getSignature().getName());
    }
 

}
