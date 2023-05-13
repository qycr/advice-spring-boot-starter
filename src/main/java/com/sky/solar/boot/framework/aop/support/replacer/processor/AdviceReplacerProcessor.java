package com.sky.solar.boot.framework.aop.support.replacer.processor;

import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public interface AdviceReplacerProcessor {

     Object beforeAdvice(Object obj, Method method, Object[] args , Object source) throws Throwable;

     Object afterAdvice(Object obj,Object[] args, MethodProxy mp) throws Throwable;


}
