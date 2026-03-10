package com.yyj.springmvc.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.yyj.springmvc.controller..*.*(..))")
    public void controllerPointcut() {}

    @Pointcut("execution(* com.yyj.springmvc.service..*.*(..))")
    public void servicePointcut() {}

    @Before("controllerPointcut()")
    public void logBeforeController(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            logger.info("=== Controller Request ===");
            logger.info("URL: {}", request.getRequestURL().toString());
            logger.info("HTTP Method: {}", request.getMethod());
            logger.info("Controller Method: {}.{}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
            logger.info("Request Args: {}", Arrays.toString(joinPoint.getArgs()));
        }
    }

    @AfterReturning(pointcut = "controllerPointcut()", returning = "result")
    public void logAfterController(JoinPoint joinPoint, Object result) {
        logger.info("=== Controller Response ===");
        logger.info("Method: {}.{}",
            joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName());
        logger.info("Response: {}", result);
    }

    @AfterThrowing(pointcut = "controllerPointcut()", throwing = "exception")
    public void logControllerException(JoinPoint joinPoint, Exception exception) {
        logger.error("=== Controller Exception ===");
        logger.error("Method: {}.{}",
            joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName());
        logger.error("Exception: {}", exception.getMessage());
    }

    @Around("servicePointcut()")
    public Object logAroundService(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("=== Service Execution ===");
        logger.info("Method: {}.{}",
            joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName());
        logger.info("Args: {}", Arrays.toString(joinPoint.getArgs()));
        
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        
        logger.info("Execution Time: {} ms", endTime - startTime);
        logger.info("Result: {}", result);
        
        return result;
    }
}
