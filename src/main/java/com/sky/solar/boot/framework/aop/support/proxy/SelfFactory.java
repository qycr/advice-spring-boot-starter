package com.sky.solar.boot.framework.aop.support.proxy;


@FunctionalInterface
public interface SelfFactory<T> {

    T getProxy() throws Exception;

}
