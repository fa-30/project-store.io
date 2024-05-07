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

public class Loggingaspoforder {

    @Pointcut("execution(* com.project.ecommerce.api.controller.OrderController..*(..))")
    public void loggingPointCutOrderController() {}

    @Before("loggingPointCutOrderController()")
    public void beforeOrderControllerMethods(JoinPoint joinPoint) {
        log.info("Before executing method in OrderController: {}", joinPoint.getSignature().getName());
    }
     @Around("loggingPointCutOrderController()")
    public Object monitorPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        log.info("PerformanceMonitoringAspect: Method " + joinPoint.getSignature() +
                " executed in " + executionTime + " milliseconds");
        return result;
    } 
    @After("loggingPointCutOrderController()")
    public void afterOrderControllerMethods(JoinPoint joinPoint) {
        log.info("After executing method in OrderController: {}", joinPoint.getSignature().getName());
    }


}