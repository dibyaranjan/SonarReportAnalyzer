package com.dibya.sonar.controller.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dibya.sonar.entity.vo.GenericJsonObject;

public class ExceptionHandlerAspectTest {
    private ExceptionHandlerAspect aop;
    private ProceedingJoinPoint joinPoint;
    
    private Mockery mockery = new Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };

    
    @Before
    public void setUp() {
        aop = new ExceptionHandlerAspect();
        joinPoint = mockery.mock(ProceedingJoinPoint.class);
    }
    
    @Test
    public void testAopWithIllegalArgumentException() throws Throwable {
        mockery.checking(new Expectations(){
            {
                oneOf(joinPoint).proceed();
                will(throwException(new IllegalArgumentException("Invalid URL!!")));
            }
        });
        
        GenericJsonObject json = aop.intercept(joinPoint);
        
        Assert.assertFalse("Sync should have failed", json.isSuccessful());
    }
    
    @Test
    public void testAopWithError() throws Throwable {
        mockery.checking(new Expectations(){
            {
                oneOf(joinPoint).proceed();
                will(throwException(new Throwable()));
            }
        });
        
        GenericJsonObject json = aop.intercept(joinPoint);
        
        Assert.assertFalse("Sync should have failed", json.isSuccessful());
    }
}
