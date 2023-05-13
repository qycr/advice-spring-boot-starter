package com.sky.solar.boot.framework.autoconfigure;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ConfigurationProperties(prefix = "spring.advice")
public class AdviceProperties {

    /**
     * whether not to open the Spring AOP dynamic proxy mode self call since the intercept  notice
     */
    private boolean enabled = true;

    /**
     * Return whether the factory should expose the proxy as a {@link ThreadLocal}.
     */
    private boolean exposeProxy = true;

    /**
     * bean scopes; from {@link com.sky.solar.boot.framework.aop.support.annotation.AdviceWay#BEAN_FACTORY} use
     */
    private List<String> scopes = of(BeanDefinition.SCOPE_SINGLETON,"refresh");

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isExposeProxy() {
        return exposeProxy;
    }

    public void setExposeProxy(boolean exposeProxy) {
        this.exposeProxy = exposeProxy;
    }

    public List<String> getScopes() {
        return scopes;
    }

    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }

    public static List<String> of(String ... scopes){
        return Stream.of(scopes).collect(Collectors.toList());
    }
}
