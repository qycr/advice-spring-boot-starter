package com.sky.solar.boot.framework.aop.support.replacer;

import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@FunctionalInterface
public interface AdviceReplacer {

    Object advice(Object obj, Method method, Object[] args, MethodProxy mp ,  Object source) throws Throwable;

}
