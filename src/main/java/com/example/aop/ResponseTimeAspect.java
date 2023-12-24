package com.example.aop;

import com.example.dto.EmployeeDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class ResponseTimeAspect {

    @Around(value = "execution(* com.example.controller.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint proceedingJoinPoint) {
        StopWatch stopWatch = new StopWatch();

        String className = proceedingJoinPoint.getTarget().getClass().getSimpleName();
        String methodName = proceedingJoinPoint.getSignature().getName();

        try {
            stopWatch.start();
            Object obj = proceedingJoinPoint.proceed();
            stopWatch.stop();
            log.info("{} method of class {} took execution time of {} ms", methodName, className, stopWatch.getTotalTimeMillis());
            return obj;
        } catch (Throwable e) {
            log.info("{} method of class {} failed due to exception:  {}", methodName, className, e.getMessage());
        }

        return null;
    }
}
