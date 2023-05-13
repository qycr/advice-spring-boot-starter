package com.sky.solar.boot.framework.aop.support.provider.self.annotation;

import com.sky.solar.boot.framework.aop.support.annotation.Advice;
import com.sky.solar.boot.framework.aop.support.annotation.AdviceWay;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Advice use class post-processing
 */
@Advice(describe = "cachePut self call interceptor ")
@CachePut
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface SelfCachePut {

    /**
     * Alias for {@link #cacheNames}.
     */
    @AliasFor(attribute = "value", annotation = CachePut.class)
    String[] value() default {};

    /**
     * Names of the caches to use for the cache put operation.
     * <p>Names may be used to determine the target cache (or caches), matching
     * the qualifier value or bean name of a specific bean definition.
     *
     * @see #value
     * @see CacheConfig#cacheNames
     * @since 4.2
     */
    @AliasFor(attribute = "cacheNames", annotation = CachePut.class)
    String[] cacheNames() default {};

    /**
     * Spring Expression Language (SpEL) expression for computing the key dynamically.
     * <p>Default is {@code ""}, meaning all method parameters are considered as a key,
     * unless a custom {@link #keyGenerator} has been set.
     * <p>The SpEL expression evaluates against a dedicated context that provides the
     * following meta-data:
     * <ul>
     * <li>{@code #result} for a reference to the result of the method invocation. For
     * supported wrappers such as {@code Optional}, {@code #result} refers to the actual
     * object, not the wrapper</li>
     * <li>{@code #root.method}, {@code #root.target}, and {@code #root.caches} for
     * references to the {@link java.lang.reflect.Method method}, target object, and
     * affected cache(s) respectively.</li>
     * <li>Shortcuts for the method name ({@code #root.methodName}) and target class
     * ({@code #root.targetClass}) are also available.
     * <li>Method arguments can be accessed by index. For instance the second argument
     * can be accessed via {@code #root.args[1]}, {@code #p1} or {@code #a1}. Arguments
     * can also be accessed by name if that information is available.</li>
     * </ul>
     */
    @AliasFor(attribute = "key", annotation = CachePut.class)
    String key() default "";

    /**
     * The bean name of the custom {@link org.springframework.cache.interceptor.KeyGenerator}
     * to use.
     * <p>Mutually exclusive with the {@link #key} attribute.
     *
     * @see CacheConfig#keyGenerator
     */
    @AliasFor(attribute = "keyGenerator", annotation = CachePut.class)
    String keyGenerator() default "";

    /**
     * The bean name of the custom {@link org.springframework.cache.CacheManager} to use to
     * create a default {@link org.springframework.cache.interceptor.CacheResolver} if none
     * is set already.
     * <p>Mutually exclusive with the {@link #cacheResolver} attribute.
     *
     * @see org.springframework.cache.interceptor.SimpleCacheResolver
     * @see CacheConfig#cacheManager
     */
    @AliasFor(attribute = "cacheManager", annotation = CachePut.class)
    String cacheManager() default "";

    /**
     * The bean name of the custom {@link org.springframework.cache.interceptor.CacheResolver}
     * to use.
     *
     * @see CacheConfig#cacheResolver
     */
    @AliasFor(attribute = "cacheResolver", annotation = CachePut.class)
    String cacheResolver() default "";

    /**
     * Spring Expression Language (SpEL) expression used for making the cache
     * put operation conditional.
     * <p>This expression is evaluated after the method has been called due to the
     * nature of the put operation and can therefore refer to the {@code result}.
     * <p>Default is {@code ""}, meaning the method result is always cached.
     * <p>The SpEL expression evaluates against a dedicated context that provides the
     * following meta-data:
     * <ul>
     * <li>{@code #result} for a reference to the result of the method invocation. For
     * supported wrappers such as {@code Optional}, {@code #result} refers to the actual
     * object, not the wrapper</li>
     * <li>{@code #root.method}, {@code #root.target}, and {@code #root.caches} for
     * references to the {@link java.lang.reflect.Method method}, target object, and
     * affected cache(s) respectively.</li>
     * <li>Shortcuts for the method name ({@code #root.methodName}) and target class
     * ({@code #root.targetClass}) are also available.
     * <li>Method arguments can be accessed by index. For instance the second argument
     * can be accessed via {@code #root.args[1]}, {@code #p1} or {@code #a1}. Arguments
     * can also be accessed by name if that information is available.</li>
     * </ul>
     */
    @AliasFor(attribute = "condition", annotation = CachePut.class)
    String condition() default "";

    /**
     * Spring Expression Language (SpEL) expression used to veto the cache put operation.
     * <p>Default is {@code ""}, meaning that caching is never vetoed.
     * <p>The SpEL expression evaluates against a dedicated context that provides the
     * following meta-data:
     * <ul>
     * <li>{@code #result} for a reference to the result of the method invocation. For
     * supported wrappers such as {@code Optional}, {@code #result} refers to the actual
     * object, not the wrapper</li>
     * <li>{@code #root.method}, {@code #root.target}, and {@code #root.caches} for
     * references to the {@link java.lang.reflect.Method method}, target object, and
     * affected cache(s) respectively.</li>
     * <li>Shortcuts for the method name ({@code #root.methodName}) and target class
     * ({@code #root.targetClass}) are also available.
     * <li>Method arguments can be accessed by index. For instance the second argument
     * can be accessed via {@code #root.args[1]}, {@code #p1} or {@code #a1}. Arguments
     * can also be accessed by name if that information is available.</li>
     * </ul>
     *
     * @since 3.2
     */
    @AliasFor(attribute = "unless", annotation = CachePut.class)
    String unless() default "";


    /**
     * Since the call to construct the current proxy object strategy
     */
    @AliasFor(attribute = "way", annotation = Advice.class)
    AdviceWay way() default AdviceWay.THREAD_LOCAL;


}
