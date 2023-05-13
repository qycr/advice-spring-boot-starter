package com.sky.solar.boot.framework.aop.support.annotation;

import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.Nullable;

public abstract class AbstractAdviceConfiguration implements ImportAware {

    @Nullable
    protected AnnotationAttributes enableAdvice;

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        this.enableAdvice = AnnotationAttributes.fromMap(
                importMetadata.getAnnotationAttributes(EnableAdvice.class.getName(), false));
        if (this.enableAdvice == null) {
            throw new IllegalArgumentException(
                    "@EnableAdvice is not present on importing class " + importMetadata.getClassName());
        }
    }

}
