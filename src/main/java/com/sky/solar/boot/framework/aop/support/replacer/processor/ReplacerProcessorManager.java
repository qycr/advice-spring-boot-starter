package com.sky.solar.boot.framework.aop.support.replacer.processor;

import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReplacerProcessorManager implements BeanFactoryAware, InitializingBean  {

    private ConfigurableListableBeanFactory beanFactory;

    private Set<AdviceReplacerProcessor> adviceReplacerProcessors = new CopyOnWriteArraySet<>();

    private static final AnnotationAwareOrderComparator comparator = AnnotationAwareOrderComparator.INSTANCE;


    public void registerProcessor(AdviceReplacerProcessor adviceReplacerProcessor){
        Assert.notNull(adviceReplacerProcessor,"must be not null");
        adviceReplacerProcessors.add(adviceReplacerProcessor);
        adviceReplacerProcessors.stream().sorted(comparator);
    }

    public void registerProcessors(Collection<AdviceReplacerProcessor> adviceReplacerProcessors){
        Assert.notEmpty(adviceReplacerProcessors,"must be not empty");
        adviceReplacerProcessors.addAll(adviceReplacerProcessors);
        adviceReplacerProcessors.stream().sorted(comparator);
    }

    public Collection<AdviceReplacerProcessor> getRegisters(){
        return this.adviceReplacerProcessors;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.adviceReplacerProcessors.size() > 0) {
            return;
        }

        final String[] beanNamesForType = beanFactory.getBeanNamesForType(AdviceReplacerProcessor.class, false, false);
        adviceReplacerProcessors.addAll(Stream.of(beanNamesForType).filter(beanName -> !ScopedProxyUtils.isScopedTarget(beanName))
                .map(beanName -> this.beanFactory.getBean(beanName, AdviceReplacerProcessor.class)).sorted(comparator).collect(Collectors.toList()));
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
            if (!(beanFactory instanceof ConfigurableListableBeanFactory)) {
                throw new IllegalArgumentException("ReplacerProcessorManager requires a ConfigurableListableBeanFactory: " + beanFactory);
            }
            this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
        }
}
