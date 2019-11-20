package io.github.zghurskyi.nosqlunit.core;

import io.github.zghurskyi.nosqlunit.core.executor.DataSetExecutor;

import java.lang.annotation.Annotation;

public interface NoSqlUnitTestContext {

    DataSetExecutor getDataSetExecutor();

    <T extends Annotation> T getAnnotation(Class<T> clazz);

    String getMethodName();

    <T extends Annotation> T getMethodAnnotation(Class<T> clazz);

    <T extends Annotation> T getClassAnnotation(Class<T> clazz);
}
