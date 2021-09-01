package com.qycr.framework.spring.boot.autoconfigure;

import com.qycr.framework.aop.support.annotation.AbstractAdviceConfiguration;
import com.qycr.framework.aop.support.config.AdviceTypeSelector;
import com.qycr.framework.aop.support.exception.AdviceExpressionException;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class AdviceExecutionAwareSelector {

    public static final AdviceExecutionAwareSelector INSTANCE = new AdviceExecutionAwareSelector();

    private BeanFactory beanFactory;

    private Environment environment;

    private AdviceExecutionAwareSelector() {

    }

    public void invokeAwareMethods(Environment environment, BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        this.environment = environment;
    }

    public Pointcut[] buildPointcut(AdviceExecutionProperties adviceExecutionProperties) {
        final AdviceExecutionProperties.AdviceFilter adviceFilter = adviceExecutionProperties.getAdviceFilter();
        Pointcut pointcut = null;
        switch (adviceFilter.getType()) {
            case ANNOTATION:
                pointcut = AdviceTypeSelector.INSTANCE.buildPointcuts(adviceExecutionProperties.getAnnotationTypes());
                break;
            case ASPECTJ:
                pointcut = aspectjPointcuts(adviceFilter.getExpression());
                break;
            case REGEX:
                pointcut = regexpPointcuts(adviceFilter.getExpression());
                break;
            case CONSUMER:
                return consumerPointcut(adviceFilter.getPointcutBeanName());
            default:
                throw new AdviceExpressionException("unknown advice");

        }
        return toArray(pointcut);
    }


    public Supplier<Advice> buildAdvice(AdviceExecutionProperties adviceExecutionProperties) {

        if (StringUtils.hasText(adviceExecutionProperties.getAdviceFilter().getAdviceBeanName())) {
            return () -> beanFactory.getBean(this.environment.resolvePlaceholders(adviceExecutionProperties.getAdviceFilter().getAdviceBeanName()), Advice.class);
        }
        return () -> AbstractAdviceConfiguration.AdviceNoop.INSTANCE;
    }


    public Pointcut aspectjPointcuts(String[] expression) {
        checkExpression(expression);
        final AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(environment.resolvePlaceholders(expression[0]));
        return pointcut;
    }

    public Pointcut regexpPointcuts(String[] expression) {
        String[] originalExpression = expression;
        checkExpression(originalExpression);
        for (int i = 0; i < expression.length; i++) {
            originalExpression[i] = environment.resolvePlaceholders(originalExpression[i]);
        }
        final JdkRegexpMethodPointcut methodPointcut = new JdkRegexpMethodPointcut();
        methodPointcut.setPatterns(originalExpression);
        return methodPointcut;
    }

    private void checkExpression(String[] expression) {
        if (Objects.isNull(expression) || expression.length == 0) {
            throw new AdviceExpressionException("expression not must be empty");
        }
    }

    private Pointcut[] consumerPointcut(String[] pointcutBeanName) {

        if (Objects.isNull(pointcutBeanName) || pointcutBeanName.length == 0) {
            throw new UnsupportedOperationException("support operation");
        }
        final Pointcut[] pointcuts = new Pointcut[pointcutBeanName.length];
        for (int i = 0; i < pointcutBeanName.length; i++) {
            pointcuts[i] = this.beanFactory.getBean(environment.resolvePlaceholders(pointcutBeanName[i]), Pointcut.class);
        }
        return pointcuts;
    }

    private Pointcut[] toArray(Pointcut pointcut) {
        return Stream.of(pointcut).toArray(Pointcut[]::new);
    }

}
