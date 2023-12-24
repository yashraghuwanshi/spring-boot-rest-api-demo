package com.example.aop;

import com.example.dto.EmployeeDto;
import com.example.util.DateTimeUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before(value = "execution(* com.example.controller.EmployeeController.*(..)))")
    public void logBeforeControllerMethod(JoinPoint joinPoint) {
        System.out.println("Request to controller method: " + joinPoint.getSignature() + " started at " + DateTimeUtil.formattedDateTime());
    }

    @After(value = "execution(* com.example.controller.EmployeeController.*(..)))")
    public void logAfterControllerMethod(JoinPoint joinPoint) {
        System.out.println("Request to controller method: " + joinPoint.getSignature() + " ended at " + DateTimeUtil.formattedDateTime());
    }

    @AfterReturning(value = "execution(* com.example.service.EmployeeServiceImpl.findByEmail(..)))", returning = "employeeDto")
    public void logAfterReturningServiceMethod(JoinPoint joinPoint, EmployeeDto employeeDto) {
        System.out.println("Request to service method: " + joinPoint.getSignature() + " was successful and employee retrieved with email: " + employeeDto.getEmail());
    }

    @AfterThrowing(value = "execution(* com.example.service.EmployeeServiceImpl.findByEmail(..)))", throwing = "ex")
    public void logAfterThrowingServiceMethod(JoinPoint joinPoint, Exception ex) {
        System.out.println("Request to service method: " + joinPoint.getSignature() + " failed due to exception:  " + ex.toString());
    }

    @Around(value = "execution(* com.example.service.EmployeeServiceImpl.findByEmail(..)))")
    public EmployeeDto logAroundServiceMethod(ProceedingJoinPoint proceedingJoinPoint) {

        ObjectMapper mapper = new ObjectMapper();

        String methodName = proceedingJoinPoint.getSignature().getName();
        String className = proceedingJoinPoint.getTarget().getClass().toString();

        Object[] args = proceedingJoinPoint.getArgs();

        try {
            log.info("Request to method {} with arguments {} in class {} was successful", methodName, mapper.writeValueAsString(args), className);
            EmployeeDto employeeDto = (EmployeeDto) proceedingJoinPoint.proceed();
            log.info("Response returned by method {}: {}", methodName, mapper.writeValueAsString(employeeDto));
            return employeeDto;
        } catch (Throwable ex) {
            System.out.println("Request to method: " + methodName + " failed due to exception:  " + ex.getMessage());
        }
        return null;
    }
}
