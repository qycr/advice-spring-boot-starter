package com.sky.solar.boot.framework.aop.support.proxy;

import org.springframework.aop.TargetSource;
import org.springframework.aop.target.SimpleBeanTargetSource;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author qycr
 */
public  class SpringBeanFactory implements SelfFactory {


    private final String beanName;

    private final TargetSource targetSource;

    private static final transient Map<String, SpringBeanFactory> CACHE = new ConcurrentHashMap<>();

    public SpringBeanFactory(String beanName, ConfigurableBeanFactory beanFactory) {
        Assert.hasText(beanName, "beanName must not be empty");
        Assert.notNull(beanFactory, "beanFactory must not be empty");
        this.beanName = beanName;
        this.targetSource = createTargetSource(beanFactory);
    }

    private TargetSource createTargetSource(ConfigurableBeanFactory beanFactory) {
        SimpleBeanTargetSource beanTargetSource = new SimpleBeanTargetSource();
        beanTargetSource.setTargetBeanName(beanName);
        beanTargetSource.setBeanFactory(beanFactory);
        return beanTargetSource;
    }


    @Override
    public Object getProxy() throws Exception {
        return targetSource.getTarget();
    }

    public static SpringBeanFactory getInstance(String beanName, ConfigurableBeanFactory beanFactory) {
        return CACHE.computeIfAbsent(beanName,(k)->new SpringBeanFactory(k,beanFactory));
    }

}
