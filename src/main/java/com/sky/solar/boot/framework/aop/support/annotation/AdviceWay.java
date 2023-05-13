package com.sky.solar.boot.framework.aop.support.annotation;

import com.sky.solar.boot.framework.aop.support.proxy.SpringBeanFactory;
import com.sky.solar.boot.framework.aop.support.proxy.ThreadLocalFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;

public enum AdviceWay {

    /**
     * Support all scope
     */
    THREAD_LOCAL {
        @Override
        public Object getProxy(RootBeanDefinition beanDefinition, String beanName,ConfigurableBeanFactory beanFactory) {
            return ThreadLocalFactory.getInstance();
        }
    },

    /***
     * temporary support only scope: singleton, refresh, support all late scope
     * not singleton / refresh scope You can consider {@link #THREAD_LOCAL}
     */
    BEAN_FACTORY{
        @Override
        public Object getProxy(RootBeanDefinition beanDefinition, String beanName,ConfigurableBeanFactory beanFactory) {
            return SpringBeanFactory.getInstance(beanName,beanFactory);
        }
    };



    public abstract Object getProxy(RootBeanDefinition beanDefinition , String beanName , ConfigurableBeanFactory beanFactory);
}
