package com.sky.solar.boot.framework.aop.support.replacer.processor;

import com.sky.solar.boot.framework.aop.support.annotation.AdviceContext;
import com.sky.solar.boot.framework.aop.support.proxy.SelfFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.util.Assert;

import java.lang.reflect.Method;

public class SimpleAdviceReplacerProcessor implements AdviceReplacerProcessor {

    @Override
    public Object beforeAdvice(Object obj, Method method, Object[] args, Object source) throws Throwable {
        Assert.notNull(source, "source must be not null");
        return invoker(method, ((SelfFactory) source).getProxy(), args);

    }

    private Object invoker(Method method, Object bean, Object[] args) throws Throwable {
        if (AopUtils.isJdkDynamicProxy(bean)) {
            //TODO... could cache if a singleton for minor performance optimization
            method = bean.getClass().getMethod(method.getName(), method.getParameterTypes());
        }
        return method.invoke(bean, args);
    }

    @Override
    public Object afterAdvice(Object obj, Object[] args, MethodProxy mp) throws Throwable {
        AdviceContext.setCurrentProxyStatus(Boolean.FALSE);
        return mp.invokeSuper(obj, args);
    }

}
