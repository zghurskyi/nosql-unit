package io.github.zghurskyi.nosqlunit.api.rule;

import io.github.zghurskyi.nosqlunit.api.annotation.Annotations;
import io.github.zghurskyi.nosqlunit.api.executor.DataSetExecutor;
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
    public <T extends Annotation> T getAnnotation(Class<T> type) {

        T classAnnotation = getClassAnnotation(type);
        T methodAnnotation = getMethodAnnotation(type);

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
    public <T extends Annotation> T getMethodAnnotation(Class<T> type) {
        if (description.isTest()) {
            return Annotations.findAnnotation(description, type);
        }

        return null;
    }

    @Override
    public <T extends Annotation> T getClassAnnotation(Class<T> type) {
        return Annotations.findAnnotation(description.getTestClass(), type);
    }
}
