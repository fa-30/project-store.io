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

public class Loggingaspofcategory {

 @Pointcut("execution(* com.project.ecommerce.api.controller.auth.CategoryController..*(..))")
    public void loggingPointCutCatController() {}

    @Before("loggingPointCutCatController()")
    public void beforeCatControllerMethods(JoinPoint joinPoint) {
        log.info("Before executing method in CategoryController: {}", joinPoint.getSignature().getName());
    }
     @Around("loggingPointCutCatController()")
    public Object monitorPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        log.info("PerformanceMonitoringAspect: Method " + joinPoint.getSignature() +
                " executed in " + executionTime + " milliseconds");
        return result;
    } 
    @After("loggingPointCutCatController()")
    public void afterAuthControllerMethods(JoinPoint joinPoint) {
        log.info("After executing method in CategoryController: {}", joinPoint.getSignature().getName());
    }


}
