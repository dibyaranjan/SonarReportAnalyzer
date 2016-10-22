package com.dibya.sonar.controller.aop;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.HibernateException;

import com.dibya.sonar.entity.vo.GenericJsonObject;

@Aspect
public class ExceptionHandlerAspect {
    private static final Logger LOGGER = Logger.getLogger(ExceptionHandlerAspect.class);

    private static final String SUCCESSFUL = "successful";
    private static final String UNSUCCESSFUL = "unsuccessful";
    private static final String MESSAGE_FORMAT = "Operation was %s took : %f mili-seconds";

    @Around("execution(* com.dibya.sonar.controller.*.*(..))")
    public GenericJsonObject intercept(ProceedingJoinPoint joinPoint) {
        long startTime = System.nanoTime();
        GenericJsonObject json = new GenericJsonObject();

        try {
            GenericJsonObject returnedJson = (GenericJsonObject) joinPoint.proceed();
            json.setSuccessful(returnedJson.isSuccessful());
            json.setData(returnedJson.getData());
        } catch (IllegalArgumentException | HibernateException e) {
            json.setSuccessful(false);
            e.printStackTrace();
            LOGGER.error(e);
            LOGGER.error(ExceptionUtils.getFullStackTrace(e));
            e.printStackTrace();
        } catch (Throwable e) {
            e.printStackTrace();
            json.setSuccessful(false);
            LOGGER.error(e);
            LOGGER.error(ExceptionUtils.getFullStackTrace(e));
        }

        long endTime = System.nanoTime();
        double timeTake = (endTime - startTime) / 1000000d;
        if (json.isSuccessful()) {
            json.setMessage(String.format(MESSAGE_FORMAT, SUCCESSFUL, timeTake));
        } else {
            json.setMessage(String.format(MESSAGE_FORMAT, UNSUCCESSFUL, timeTake));
        }

        return json;
    }
}
