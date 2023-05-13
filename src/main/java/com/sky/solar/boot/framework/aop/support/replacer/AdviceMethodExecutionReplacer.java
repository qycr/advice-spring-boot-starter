package com.sky.solar.boot.framework.aop.support.replacer;

import com.sky.solar.boot.framework.aop.support.annotation.AdviceContext;
import com.sky.solar.boot.framework.aop.support.replacer.processor.AdviceReplacerProcessor;
import com.sky.solar.boot.framework.aop.support.replacer.processor.ReplacerProcessorManager;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class AdviceMethodExecutionReplacer implements AdviceReplacer {

    public static final String COMMON_ADVICE_REPLACER = "internalCommonAdviceReplacer";

    private final ReplacerProcessorManager replacerProcessorManager;

    public AdviceMethodExecutionReplacer(ReplacerProcessorManager replacerProcessorManager) {
        this.replacerProcessorManager = replacerProcessorManager;
    }

    @Override
    public Object advice(Object obj, Method method, Object[] args, MethodProxy mp, Object source) throws Throwable {

        final boolean proxyStatus = AdviceContext.currentProxyStatus();
        if (proxyStatus) {
            return applyAdviceAfterHandler(obj, args, mp);
        }
        return applyAdviceBeforeHandler(obj, method, args, source);
    }


    public Object applyAdviceAfterHandler(Object obj, Object[] args, MethodProxy mp) throws Throwable {
        Object returnObject = null;
        for (AdviceReplacerProcessor replacerProcessor : replacerProcessorManager.getRegisters()) {
            returnObject = replacerProcessor.afterAdvice(obj, args, mp);
        }
        return returnObject;
    }


    public Object applyAdviceBeforeHandler(Object obj, Method method, Object[] args, Object source) throws Throwable {
        Object returnObject = null;
        for (AdviceReplacerProcessor replacerProcessor : replacerProcessorManager.getRegisters()) {
            returnObject = replacerProcessor.beforeAdvice(obj, method, args, source);
        }
        return returnObject;
    }

}

