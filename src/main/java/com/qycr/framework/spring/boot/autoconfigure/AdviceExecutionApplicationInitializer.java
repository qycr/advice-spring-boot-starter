package com.qycr.framework.spring.boot.autoconfigure;

import com.qycr.framework.aop.support.proxy.LocalCglibSubclassingInstantiationStrategy;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;

public class AdviceExecutionApplicationInitializer implements ApplicationContextInitializer , Ordered {


    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        final ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        if(beanFactory instanceof AbstractAutowireCapableBeanFactory){
            ((AbstractAutowireCapableBeanFactory)beanFactory).setInstantiationStrategy(new LocalCglibSubclassingInstantiationStrategy());
        }
        AdviceExecutionAwareSelector.INSTANCE.invokeAwareMethods(applicationContext.getEnvironment(),applicationContext.getBeanFactory());
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}
