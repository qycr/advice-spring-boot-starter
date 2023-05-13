package com.sky.solar.boot.framework.aop.support.annotation;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(SelfAdviceConfiguration.class)
@Deprecated
public @interface EnableAdvice {

    /**
     * Indicate that the proxy should be exposed by the AOP framework as a {@code ThreadLocal}
     * for retrieval via the {@link org.springframework.aop.framework.AopContext} class.
     * Off by default, i.e. no guarantees that {@code AopContext} access will work.
     *
     * <p> notice: when we choose to use this {@link AdviceWay#THREAD_LOCAL} strategy,
     *     there is no other item set exposure proxy situation we must make the pledge
     *     that the attribute value is always true
     */
    boolean exposeProxy() default true;


    /**
     * Indicate the order in which the {@link MethodInvokerAdviceExecutionPostProcessor}
     * should be applied.
     * <p>The default is {@link Ordered#LOWEST_PRECEDENCE} in order to run
     * after all other post-processors, so that it can add an advisor to
     * existing proxies rather than double-proxy.
     */
    int order() default Ordered.LOWEST_PRECEDENCE;


    /**
     *  when we choose to use {@link AdviceWay#BEAN_FACTORY} strategy
     *  <p>notice:Calls to the scope of the bean processing by default
     *  only supports single cases and refresh
     */
    String[] scopes() default {BeanDefinition.SCOPE_SINGLETON,"refresh"};

}
