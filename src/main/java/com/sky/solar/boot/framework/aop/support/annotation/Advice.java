package com.sky.solar.boot.framework.aop.support.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface Advice {

    String value() default "";

    AdviceWay way() default AdviceWay.THREAD_LOCAL;

    String describe() default "";

    boolean selfCallingAdvice() default  true;

}
