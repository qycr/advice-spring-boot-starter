package com.sky.solar.boot.framework.aop.support.annotation;

import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.autoproxy.AbstractBeanFactoryAwareAdvisingPostProcessor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
public class MethodInvokerAdviceExecutionPostProcessor extends AbstractBeanFactoryAwareAdvisingPostProcessor {


    private Class<? extends Annotation> annotationType;

    private final transient Pointcut pointcut = new StaticMethodMatcherPointcut() {
        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            Assert.notNull(annotationType, "must be not null");
            return AnnotatedElementUtils.findMergedAnnotation(method, annotationType) != null;
        }
    };


    public void setAnnotationType(Class<? extends Annotation> annotationType) {
        this.annotationType = annotationType;
    }

    @Override
    protected ProxyFactory prepareProxyFactory(Object bean, String beanName) {
        final ProxyFactory proxyFactory = super.prepareProxyFactory(bean, beanName);
        if (!proxyFactory.isExposeProxy() && this.isExposeProxy()) {
            proxyFactory.setExposeProxy(true);
        }
        return proxyFactory;
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        bean = super.postProcessAfterInitialization(bean, beanName);
        if (this.isExposeProxy() && bean instanceof Advised && !((Advised) bean).isExposeProxy()) {
            Advised advised = (Advised) bean;
            advised.setExposeProxy(this.isExposeProxy());
        }
        return bean;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        super.setBeanFactory(beanFactory);
        this.advisor = new DefaultPointcutAdvisor(pointcut, new MethodInvokerAdviceExecutionInterceptor());
    }

}