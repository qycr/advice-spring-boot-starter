package com.sky.solar.boot.framework.autoconfigure;


import com.sky.solar.boot.framework.aop.support.annotation.*;
import com.sky.solar.boot.framework.aop.support.replacer.AdviceMethodExecutionReplacer;
import com.sky.solar.boot.framework.aop.support.replacer.processor.AdviceReplacerProcessor;
import com.sky.solar.boot.framework.aop.support.replacer.processor.ReplacerProcessorManager;
import com.sky.solar.boot.framework.aop.support.replacer.processor.SimpleAdviceReplacerProcessor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.core.env.Environment;

import java.util.stream.Collectors;


@Configuration
@EnableConfigurationProperties(AdviceProperties.class)
@ConditionalOnClass({AdviceWay.class})
@ConditionalOnProperty(prefix = "spring.advice" , name = "enabled", havingValue = "true" , matchIfMissing = true)
@ConditionalOnMissingBean(SelfAdviceConfiguration.class)
public class AdviceAutoConfiguration {


    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @ConditionalOnMissingBean
    public static MethodInvokerAdviceExecutionPostProcessor methodInvokerAdviceExecutionPostProcessor(Environment environment , BeanFactory beanFactory){
        MethodInvokerAdviceExecutionPostProcessor methodInvokerAdviceExecutionPostProcessor = new MethodInvokerAdviceExecutionPostProcessor();
        methodInvokerAdviceExecutionPostProcessor.setAnnotationType(Advice.class);
        if(Boolean.parseBoolean(environment.resolvePlaceholders("${spring.advice.expose-proxy:true}"))){
            methodInvokerAdviceExecutionPostProcessor.setExposeProxy(Boolean.TRUE);
        }
        methodInvokerAdviceExecutionPostProcessor.setBeanFactory(beanFactory);
        return methodInvokerAdviceExecutionPostProcessor;
    }



    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @ConditionalOnMissingBean
    public static AdviceMethodHandlerBeanPostProcessor adviceMethodHandlerBeanPostProcessor(Environment environment){
        AdviceMethodHandlerBeanPostProcessor adviceMethodHandlerBeanPostProcessor = new AdviceMethodHandlerBeanPostProcessor();
        AdviceProperties adviceProperties = Binder.get(environment).bindOrCreate("spring.advice", Bindable.of(AdviceProperties.class));
        adviceMethodHandlerBeanPostProcessor.setScopes(adviceProperties.getScopes());
        return adviceMethodHandlerBeanPostProcessor;
    }

    @Bean(AdviceMethodExecutionReplacer.COMMON_ADVICE_REPLACER)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @ConditionalOnMissingBean
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



}
