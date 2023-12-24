package com.example.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Objects;

@Aspect
@Component
@Slf4j
public class ResponseTimeAspect {
    @Around(value = "execution(* com.example.*.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();

        String className = AopProxyUtils.ultimateTargetClass(proceedingJoinPoint.getTarget()).getSimpleName();
        String methodName = proceedingJoinPoint.getSignature().getName();

        try {
            stopWatch.start();
            Object obj = proceedingJoinPoint.proceed();
            stopWatch.stop();
            log.info("{}() method of class {} took execution time of {} ms", methodName, className, stopWatch.getTotalTimeMillis());
            return obj;
        } catch (Throwable e) {
            log.info("{}() method of class {} failed due to exception:  {}", methodName, className, e.getMessage());
            throw e;
        }
    }
}