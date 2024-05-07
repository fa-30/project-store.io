package com.project.ecommerce.ASOP;
import com.project.ecommerce.api.controller.auth.AuthenticationController;
import lombok.extern.slf4j.Slf4j;

import org.apache.catalina.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@SuppressWarnings("unused")
@Aspect
@Component
@Slf4j

public class Loggingaspofproduct {

    @Pointcut("execution(* com.project.ecommerce.api.controller.auth.ProductController..*(..))")
    public void loggingPointCutProdController() {}

    @Before("loggingPointCutProdController()")
    public void beforeProductControllerMethods(JoinPoint joinPoint) {
        log.info("Before executing method in ProductController: {}", joinPoint.getSignature().getName());
    }
    @Around("loggingPointCutProdController()")
    public Object monitorPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        log.info("PerformanceMonitoringAspect: Method " + joinPoint.getSignature() +
                " executed in " + executionTime + " milliseconds");
        return result;
    } 
    @After("loggingPointCutProdController()")
    public void afterAuthControllerMethods(JoinPoint joinPoint) {
        log.info("After executing method in ProductController: {}", joinPoint.getSignature().getName());
    }


}
