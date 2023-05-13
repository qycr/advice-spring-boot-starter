package com.sky.solar.boot.framework.aop.support.proxy;

import org.springframework.aop.framework.AopContext;

public class ThreadLocalFactory implements SelfFactory {

    private static ThreadLocalFactory instance = new ThreadLocalFactory();

    private ThreadLocalFactory() {

    }

    public static ThreadLocalFactory getInstance() {
        return instance;
    }

    @Override
    public Object getProxy() throws Exception {
        return AopContext.currentProxy();
    }
}
