package com.sky.solar.boot.framework.aop.support.annotation;

import org.springframework.core.Ordered;

import java.lang.reflect.Method;

public abstract class AbstractAdviceExecutionInterceptor implements Ordered {

    private Integer order=Integer.MAX_VALUE;

    protected  Object execute(AdviceMethodExecutionInvoker invoker, Object target, Method method, Object[] args) throws Throwable{
        throw new UnsupportedOperationException("This operation is not supported and needs to be implemented by subclasses");
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
