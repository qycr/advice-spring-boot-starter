package com.sky.solar.boot.framework.aop.support.provider.self.annotation;

import com.sky.solar.boot.framework.aop.support.annotation.Advice;
import com.sky.solar.boot.framework.aop.support.annotation.AdviceWay;
import org.springframework.core.annotation.AliasFor;
import org.springframework.scheduling.annotation.Async;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author qycr
 * @see Advice use class post-processing
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Async
@Advice(describe = "async  self call  interceptor")
public @interface SelfAsync {

    /**
     * A qualifier value for the specified asynchronous operation(s).
     * <p>May be used to determine the target executor to be used when executing
     * the asynchronous operation(s), matching the qualifier value (or the bean
     * name) of a specific {@link java.util.concurrent.Executor Executor} or
     * {@link org.springframework.core.task.TaskExecutor TaskExecutor}
     * bean definition.
     * <p>When specified on a class-level {@code @Async} annotation, indicates that the
     * given executor should be used for all methods within the class. Method-level use
     * of {@code Async#value} always overrides any value set at the class level.
     */
    @AliasFor(attribute = "value", annotation = Async.class)
    String value() default "";


    /**
     * Since the call to construct the current proxy object strategy
     */
    @AliasFor(attribute = "way" , annotation = Advice.class)
    AdviceWay way() default AdviceWay.BEAN_FACTORY;

}
