package com.yyj.springmvc.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志切面
 *
 * @author yan yinjie
 * @date 2026/03/10
 */
@Aspect
@Component
public class LoggingAspect {
    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * 定义切入点
     */
    @Pointcut("execution(* com.yyj.springmvc.controller.*.*(..))")
    public void controllerPointcut() {
    }

    /**
     * 方法执行前
     *
     * @param joinPoint 连接点
     */
    @Before("controllerPointcut()")
    public void beforeMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.info("开始执行方法: {}", methodName);
    }

    /**
     * 方法执行后
     *
     * @param joinPoint 连接点
     * @param result    结果
     */
    @AfterReturning(pointcut = "controllerPointcut()", returning = "result")
    public void afterReturningMethod(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        log.info("方法执行完成: {}, 返回结果: {}", methodName, result);
    }

    /**
     * 方法执行异常
     *
     * @param joinPoint 连接点
     * @param e         异常
     */
    @AfterThrowing(pointcut = "controllerPointcut()", throwing = "e")
    public void afterThrowingMethod(JoinPoint joinPoint, Exception e) {
        String methodName = joinPoint.getSignature().getName();
        log.error("方法执行异常: {}, 异常信息: {}", methodName, e.getMessage());
    }
}
