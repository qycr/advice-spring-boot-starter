package com.qycr.framework.spring.boot.autoconfigure;

import com.qycr.framework.aop.support.annotation.AdviceMethodHandlerBeanPostProcessor;
import com.qycr.framework.aop.support.annotation.MethodInvokerAdviceExecutionPostProcessor;
import com.qycr.framework.aop.support.config.AdviceType;
import com.qycr.framework.aop.support.config.AdviceTypeSelector;
import com.qycr.framework.aop.support.engine.inject.AdviceProxyAwarePostProcessor;
import com.qycr.framework.aop.support.replacer.AdviceMethodExecutionReplacer;
import com.qycr.framework.aop.support.replacer.processor.AdviceReplacerProcessor;
import com.qycr.framework.aop.support.replacer.processor.SimpleAdviceReplacerProcessor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;


@Configuration(proxyBeanMethods = false)//5.2
@ConditionalOnClass({AdviceType.class})
@ConditionalOnProperty( prefix = AdviceExecutionProperties.ADVICE_PREFIX , name = "enable" , havingValue = "true" )
@EnableConfigurationProperties({AdviceExecutionProperties.class})
public class AdviceExecutionAutoConfiguration {



    @Bean
    @ConditionalOnClass(MethodInvokerAdviceExecutionPostProcessor.class)
    @ConditionalOnMissingBean(MethodInvokerAdviceExecutionPostProcessor.class)
    public static MethodInvokerAdviceExecutionPostProcessor methodInvokerAdviceExecutionPostProcessor(AdviceExecutionProperties adviceExecutionProperties){
        final MethodInvokerAdviceExecutionPostProcessor processor = new MethodInvokerAdviceExecutionPostProcessor(AdviceExecutionAwareSelector.INSTANCE.buildAdvice(adviceExecutionProperties), AdviceExecutionAwareSelector.INSTANCE.buildPointcut(adviceExecutionProperties));
        if(adviceExecutionProperties.isExposeProxy()){
            processor.setExposeProxy(adviceExecutionProperties.isExposeProxy());
        }
        return processor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @ConditionalOnMissingBean(AdviceProxyAwarePostProcessor.class)
    public static AdviceProxyAwarePostProcessor adviceProxyAwarePostProcessor() {
        return new AdviceProxyAwarePostProcessor();
    }


    @Bean(AdviceMethodExecutionReplacer.COMMON_ADVICE_REPLACER)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @ConditionalOnMissingBean(name = AdviceMethodExecutionReplacer.COMMON_ADVICE_REPLACER )
    public  AdviceMethodExecutionReplacer adviceMethodExecutionReplacer() {
        return new AdviceMethodExecutionReplacer();
    }


    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @ConditionalOnMissingBean(AdviceMethodHandlerBeanPostProcessor.class)
    public  AdviceMethodHandlerBeanPostProcessor adviceMethodHandlerBeanPostProcessor(AdviceExecutionProperties adviceExecutionProperties) {
        AdviceMethodHandlerBeanPostProcessor adviceMethodHandlerBeanPostProcessor =  new AdviceMethodHandlerBeanPostProcessor();
        adviceMethodHandlerBeanPostProcessor.setPointcut(AdviceTypeSelector.INSTANCE.buildPointcut(AdviceExecutionAwareSelector.INSTANCE.buildPointcut(adviceExecutionProperties)));
        return adviceMethodHandlerBeanPostProcessor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @ConditionalOnMissingBean(AdviceReplacerProcessor.class)
    public  SimpleAdviceReplacerProcessor simpleAdviceReplacerProcessor(){
        return new SimpleAdviceReplacerProcessor();
    }



}
