package com.truphone.challenge.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class ResponseLoggingAspect {

    private ObjectMapper objectMapper;

    @Pointcut("execution(public * @org.springframework.web.bind.annotation.RequestMapping com.truphone.challenge.controller.*Controller.*(..))")
    public void perform() {
    }

    @Around("perform()")
    public Object logResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        String signatureName = joinPoint.getSignature().getDeclaringType().getSimpleName() + "." + joinPoint.getSignature().getName();
        Object returnValue = joinPoint.proceed();

        log.info("Response from {}: {}", signatureName, objectMapper.writeValueAsString(returnValue));
        return returnValue;
    }
}