package com.sky.solar.boot.framework.aop.support.annotation;

import org.springframework.core.NamedThreadLocal;


public final  class AdviceContext {

    private static final ThreadLocal<Boolean> currentProxy = new NamedThreadLocal<Boolean>("current advice status"){
        @Override
        protected Boolean initialValue() {
            return Boolean.FALSE;
        }
    };

    private AdviceContext() {

    }

    public static boolean currentProxyStatus() throws IllegalStateException {
        return currentProxy.get();
    }

    public static boolean setCurrentProxyStatus( boolean status) {
        boolean old = currentProxy.get();
        if (!old || status) {
            currentProxy.set(status);
        }
        else {
            currentProxy.remove();
        }
        return old;
    }
}
