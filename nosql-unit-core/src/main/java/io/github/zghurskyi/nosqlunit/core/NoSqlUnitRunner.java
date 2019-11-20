package io.github.zghurskyi.nosqlunit.core;

import io.github.zghurskyi.nosqlunit.api.annotation.ExpectedDataSet;
import io.github.zghurskyi.nosqlunit.api.annotation.InitialDataSet;
import io.github.zghurskyi.nosqlunit.api.properties.DataSetProperties;
import io.github.zghurskyi.nosqlunit.core.executor.DataSetExecutor;


public class NoSqlUnitRunner {

    public void runBeforeTest(NoSqlUnitTestContext context) {

        DataSetExecutor executor = context.getDataSetExecutor();
        InitialDataSet initialDataSet = context.getAnnotation(InitialDataSet.class);

        if (initialDataSet != null) {
            try {
                DataSetProperties dataSetProperties = new DataSetProperties(initialDataSet);
                executor.createDataSet(dataSetProperties);
            } catch (Throwable throwable) {
                String message = String.format("Can't create initial dataSet for test '%s'.", context.getMethodName());
                throw new IllegalStateException(message, throwable);
            }
        }
    }

    public void runAfterTest(NoSqlUnitTestContext context) {

        DataSetExecutor executor = context.getDataSetExecutor();
        ExpectedDataSet expectedDataSet = context.getAnnotation(ExpectedDataSet.class);

        if (expectedDataSet != null) {
            try {
                DataSetProperties dataSetProperties = new DataSetProperties(expectedDataSet.value());
                executor.compareCurrentWithExpectedDataSet(dataSetProperties);
            } catch (Throwable throwable) {
                String message = String.format("Can't create expected dataSet for test '%s'.", context.getMethodName());
                throw new IllegalStateException(message, throwable);
            }
        }
    }

    public void tearDown(NoSqlUnitTestContext context) {

        DataSetExecutor executor = context.getDataSetExecutor();
        InitialDataSet initialDataSet = context.getAnnotation(InitialDataSet.class);

        if (initialDataSet != null) {
            DataSetProperties dataSetProperties = new DataSetProperties(initialDataSet);
            executor.clearStorage(dataSetProperties);
        }
    }
}
