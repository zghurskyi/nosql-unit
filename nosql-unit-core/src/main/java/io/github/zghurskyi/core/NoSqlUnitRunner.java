package io.github.zghurskyi.core;

import io.github.zghurskyi.core.api.ExpectedDataSet;
import io.github.zghurskyi.core.api.InitialDataSet;
import io.github.zghurskyi.core.configuration.DataSetConfig;
import io.github.zghurskyi.core.executor.DataSetExecutor;


public class NoSqlUnitRunner {

    public void runBeforeTest(NoSqlUnitTestContext context) {

        DataSetExecutor executor = context.getDataSetExecutor();
        InitialDataSet initialDataSet = context.getAnnotation(InitialDataSet.class);

        if (initialDataSet != null) {
            try {
                DataSetConfig dataSetConfig = new DataSetConfig(initialDataSet);
                executor.createDataSet(dataSetConfig);
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
                DataSetConfig dataSetConfig = new DataSetConfig(expectedDataSet.value());
                executor.compareCurrentWithExpectedDataSet(dataSetConfig);
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
            DataSetConfig dataSetConfig = new DataSetConfig(initialDataSet);
            executor.clearStorage(dataSetConfig);
        }
    }
}
