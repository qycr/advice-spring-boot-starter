package com.qycr.framework.spring.boot.autoconfigure;

import com.qycr.framework.aop.support.config.AdviceType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.lang.annotation.Annotation;

@ConfigurationProperties( prefix = AdviceExecutionProperties.ADVICE_PREFIX)
public class AdviceExecutionProperties {


    public static final String ADVICE_PREFIX="spring.advice";

    /**
     * custom annotation
     */
    private Class<? extends Annotation>[] annotationTypes=new Class[0];

    /**
     * open exposure agent class
     */
    private boolean  exposeProxy=false;

    /**
     * advice filter
     */
    private AdviceFilter adviceFilter = new AdviceFilter();


    public static class AdviceFilter {

        /**
         * advice type
         */
        private AdviceType type = AdviceType.ANNOTATION;

        /**
         * custom point bean name
         */
        private String[] pointcutBeanName;

        /**
         * expression  aspectj | regex
         */
        private String[] expression;

        /**
         * advice bean name
         */
        private String adviceBeanName;


        public AdviceType getType() {
            return type;
        }

        public void setType(AdviceType type) {
            this.type = type;
        }

        public String[] getPointcutBeanName() {
            return pointcutBeanName;
        }

        public void setPointcutBeanName(String[] pointcutBeanName) {
            this.pointcutBeanName = pointcutBeanName;
        }

        public String[] getExpression() {
            return expression;
        }

        public void setExpression(String[] expression) {
            this.expression = expression;
        }

        public String getAdviceBeanName() {
            return adviceBeanName;
        }

        public void setAdviceBeanName(String adviceBeanName) {
            this.adviceBeanName = adviceBeanName;
        }

    }

    public Class<? extends Annotation>[] getAnnotationTypes() {
        return annotationTypes;
    }

    public void setAnnotationTypes(Class<? extends Annotation>[] annotationTypes) {
        this.annotationTypes = annotationTypes;
    }

    public boolean isExposeProxy() {
        return exposeProxy;
    }

    public void setExposeProxy(boolean exposeProxy) {
        this.exposeProxy = exposeProxy;
    }

    public AdviceFilter getAdviceFilter() {
        return adviceFilter;
    }

    public void setAdviceFilter(AdviceFilter adviceFilter) {
        this.adviceFilter = adviceFilter;
    }
}
