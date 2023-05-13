package com.sky.solar.boot.framework.aop.support.annotation;

@FunctionalInterface
public interface AdviceMethodExecutionInvoker {

    Object invoke() throws Throwable;

}
