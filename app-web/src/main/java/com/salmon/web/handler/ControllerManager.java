package com.salmon.web.handler;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerManager {

    @Pointcut("execution(* com..controller..*.*(..))")
    private void pointcut(){}

    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint){
        System.out.println(joinPoint.getTarget());
    }
}
