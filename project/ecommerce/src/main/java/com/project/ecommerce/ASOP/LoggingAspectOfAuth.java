package com.project.ecommerce.ASOP; // Adjust package name to match the expected package

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
public class LoggingAspectOfAuth {

    @Pointcut("execution(* com.project.ecommerce.api.controller.auth.AuthenticationController..*(..))")
    public void loggingPointCutAuthController() {}

    @Before("loggingPointCutAuthController()")
    public void beforeAuthControllerMethods(JoinPoint joinPoint) {
        log.info("Before executing method in AuthenticationController: {}", joinPoint.getSignature().getName());
    }

    @Around("loggingPointCutAuthController()")
    public Object monitorPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        log.info("PerformanceMonitoringAspect: Method " + joinPoint.getSignature() +
                " executed in " + executionTime + " milliseconds");
        return result;
    } 
    
    @After("loggingPointCutAuthController()")
    public void afterAuthControllerMethods(JoinPoint joinPoint) {
        log.info("After executing method in AuthenticationController: {}", joinPoint.getSignature().getName());
    }
   

}