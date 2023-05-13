package com.sky.solar.boot.framework.aop.support.annotation;

import com.sky.solar.boot.framework.aop.support.proxy.AdviceCglibSubclassingInstantiationStrategy;
import com.sky.solar.boot.framework.aop.support.replacer.AdviceMethodExecutionReplacer;
import com.sky.solar.boot.framework.aop.support.replacer.processor.AdviceReplacerProcessor;
import com.sky.solar.boot.framework.aop.support.replacer.processor.ReplacerProcessorManager;
import com.sky.solar.boot.framework.aop.support.replacer.processor.SimpleAdviceReplacerProcessor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class SelfAdviceConfiguration extends AbstractAdviceConfiguration{



    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public  MethodInvokerAdviceExecutionPostProcessor methodInvokerAdviceExecutionPostProcessor(BeanFactory beanFactory){
        MethodInvokerAdviceExecutionPostProcessor methodInvokerAdviceExecutionPostProcessor = new MethodInvokerAdviceExecutionPostProcessor();
        methodInvokerAdviceExecutionPostProcessor.setAnnotationType(Advice.class);
        if(enableAdvice.getBoolean("exposeProxy")){
            methodInvokerAdviceExecutionPostProcessor.setExposeProxy(Boolean.TRUE);
        }
        methodInvokerAdviceExecutionPostProcessor.setBeanFactory(beanFactory);
        methodInvokerAdviceExecutionPostProcessor.setOrder(enableAdvice.getNumber("order"));
        return methodInvokerAdviceExecutionPostProcessor;
    }



    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public  AdviceMethodHandlerBeanPostProcessor adviceMethodHandlerBeanPostProcessor(){
        AdviceMethodHandlerBeanPostProcessor adviceMethodHandlerBeanPostProcessor = new AdviceMethodHandlerBeanPostProcessor();
        adviceMethodHandlerBeanPostProcessor.setScopes(Stream.of(enableAdvice.getStringArray("scopes")).collect(Collectors.toList()));
        return adviceMethodHandlerBeanPostProcessor;
    }

    @Bean(AdviceMethodExecutionReplacer.COMMON_ADVICE_REPLACER)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public AdviceMethodExecutionReplacer adviceMethodExecutionReplacer(ReplacerProcessorManager replacerProcessorManager){
        return new AdviceMethodExecutionReplacer(replacerProcessorManager);
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public ReplacerProcessorManager replacerProcessorManager(ObjectProvider<AdviceReplacerProcessor> objectProvider){
        ReplacerProcessorManager processorManager = new ReplacerProcessorManager();
        processorManager.registerProcessors(objectProvider.orderedStream().collect(Collectors.toList()));
        return processorManager;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public SimpleAdviceReplacerProcessor simpleAdviceReplacerProcessor(){
        return new SimpleAdviceReplacerProcessor();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public  static BeanFactoryPostProcessor adviceBeanFactoryPostProcessor(){
       return beanFactory -> AbstractAutowireCapableBeanFactory.class.cast(beanFactory).setInstantiationStrategy(new AdviceCglibSubclassingInstantiationStrategy());
    }

}
