package io.github.zghurskyi.core;

import io.github.zghurskyi.core.executor.DataSetExecutor;
import io.github.zghurskyi.core.utils.AnnotationUtils;
import org.junit.runner.Description;

import java.lang.annotation.Annotation;

public class DefaultNoSqlUnitTestContext implements NoSqlUnitTestContext {

    private final DataSetExecutor executor;
    private final Description description;

    public DefaultNoSqlUnitTestContext(DataSetExecutor executor, Description description) {
        this.executor = executor;
        this.description = description;
    }

    @Override
    public DataSetExecutor getDataSetExecutor() {
        return executor;
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> clazz) {

        T classAnnotation = getClassAnnotation(clazz);
        T methodAnnotation = getMethodAnnotation(clazz);

        if (methodAnnotation != null) {
            return methodAnnotation;
        }

        return classAnnotation;
    }

    @Override
    public String getMethodName() {
        return description.getMethodName();
    }

    @Override
    public <T extends Annotation> T getMethodAnnotation(Class<T> clazz) {
        if (description.isTest()) {
            return AnnotationUtils.findAnnotation(description, clazz);
        }

        return null;
    }

    @Override
    public <T extends Annotation> T getClassAnnotation(Class<T> clazz) {
        return AnnotationUtils.findAnnotation(description.getTestClass(), clazz);
    }
}
