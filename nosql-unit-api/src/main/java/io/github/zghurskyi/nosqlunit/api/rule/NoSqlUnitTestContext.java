package io.github.zghurskyi.nosqlunit.api.rule;

import io.github.zghurskyi.nosqlunit.api.executor.DataSetExecutor;

import java.lang.annotation.Annotation;

public interface NoSqlUnitTestContext {

    DataSetExecutor getDataSetExecutor();

    <T extends Annotation> T getAnnotation(Class<T> type);

    String getMethodName();

    <T extends Annotation> T getMethodAnnotation(Class<T> type);

    <T extends Annotation> T getClassAnnotation(Class<T> type);
}
