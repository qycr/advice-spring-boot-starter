package com.sky.solar.boot.framework.autoconfigure;

import com.sky.solar.boot.framework.aop.support.proxy.AdviceCglibSubclassingInstantiationStrategy;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;


public class AdviceApplicationContextInitializer implements ApplicationContextInitializer {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableListableBeanFactory configurableListableBeanFactory = applicationContext.getBeanFactory();
        AbstractAutowireCapableBeanFactory.class.cast(configurableListableBeanFactory).setInstantiationStrategy(new AdviceCglibSubclassingInstantiationStrategy());
    }
}
