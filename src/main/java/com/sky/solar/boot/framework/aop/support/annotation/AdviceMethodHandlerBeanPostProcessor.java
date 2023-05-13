package com.sky.solar.boot.framework.aop.support.annotation;

import com.sky.solar.boot.framework.aop.support.replacer.AdviceMethodExecutionReplacer;
import com.sky.solar.boot.framework.aop.support.replacer.AdviceOverride;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class AdviceMethodHandlerBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter implements PriorityOrdered, BeanFactoryAware {


    private ConfigurableBeanFactory beanFactory;

    private List<String> scopes;

    private final Set<String> adviceMethodsChecked = Collections.newSetFromMap(new ConcurrentHashMap<>(256));


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (!(beanFactory instanceof ConfigurableBeanFactory)) {
            throw new IllegalArgumentException(String.format("AdviceMethodHandlerBeanPostProcessor requires a ConfigurableBeanFactory: %s", beanFactory));
        }
        this.beanFactory = (ConfigurableBeanFactory) beanFactory;
    }


    /**
     * The processing method of annotation temporarily, class notes later
     */
    @Override
    public Constructor<?>[] determineCandidateConstructors(Class<?> beanClass, String beanName) throws BeansException {

        if (!adviceMethodsChecked.contains(beanName)) {
            Assert.notEmpty(scopes, "must be not empty");

            try {
                /*
                 * Reminder: Class level will not be processed temporarily, and will be iteratively processed in subsequent small versions.
                 */
                Class<?> targetClass = beanClass;
                do {
                    ReflectionUtils.doWithMethods(targetClass, method -> {
                        Advice mergedAnnotation = AnnotatedElementUtils.findMergedAnnotation(method, Advice.class);
                        if (isEligible(mergedAnnotation)) {
                            return;
                        }
                        RootBeanDefinition beanDefinition = (RootBeanDefinition) beanFactory.getMergedBeanDefinition(beanName);
                        if (!(scopes.contains(beanDefinition.getScope())) && !(mergedAnnotation.way().equals(AdviceWay.THREAD_LOCAL))) {
                            return;
                        }
                        AdviceOverride adviceOverride = new AdviceOverride(method.getName(), AdviceMethodExecutionReplacer.COMMON_ADVICE_REPLACER, false);
                        adviceOverride.setSource(mergedAnnotation.way().getProxy(beanDefinition, beanName, beanFactory));
                        beanDefinition.getMethodOverrides().addOverride(adviceOverride);

                    });

                    targetClass = targetClass.getSuperclass();

                } while (targetClass != null && targetClass != Object.class);

            } catch (Exception e) {
                e.printStackTrace();
            }

            this.adviceMethodsChecked.add(beanName);

        }
        return super.determineCandidateConstructors(beanClass, beanName);

    }


    private boolean isEligible(Advice advice) {
        return Objects.isNull(advice) || !advice.selfCallingAdvice();
    }


    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }


    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 3;
    }


}