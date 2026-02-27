package com.example.api.application.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ExceptionHandlingAspect {

    @Pointcut("within(com.example.api.application..*) && !within(com.example.api.application.aspect..*)")
    public void applicationLayer() {
    }

    @AfterThrowing(pointcut = "applicationLayer()", throwing = "ex")
    public void handleException(Throwable ex) {
        log.error("Unhandled exception caught by aspect: {} - {}", ex.getClass().getSimpleName(), ex.getMessage());
    }
}
