package io.github.zghurskyi.core;

import io.github.zghurskyi.core.api.DataSet;
import io.github.zghurskyi.core.api.DataSetExecutor;
import io.github.zghurskyi.core.api.ExpectedDataSet;
import io.github.zghurskyi.core.configuration.DataSetConfig;


public class NoSqlUnitRunner {

    public void runBeforeTest(NoSqlUnitTestContext context) {

        DataSetExecutor executor = context.getDataSetExecutor();
        DataSet dataSet = context.getAnnotation(DataSet.class);

        if (dataSet != null) {
            try {
                DataSetConfig dataSetConfig = new DataSetConfig(dataSet);
                executor.createDataSet(dataSetConfig);
            } catch (Throwable throwable) {
                String message = String.format("Can't create initial dataset for test '%s'.", context.getMethodName());
                throw new RuntimeException(message, throwable);
            }
        }
    }

    public void runAfterTest(NoSqlUnitTestContext context) {

        DataSetExecutor executor = context.getDataSetExecutor();
        ExpectedDataSet expectedDataSet = context.getAnnotation(ExpectedDataSet.class);

        if (expectedDataSet != null) {
            try {
                DataSetConfig dataSetConfig = new DataSetConfig(expectedDataSet.value());
                executor.compareCurrentDataSetWith(dataSetConfig);
            } catch (Throwable throwable) {
                String message = String.format("Can't create expected dataset for test '%s'.", context.getMethodName());
                throw new RuntimeException(message, throwable);
            }
        }
    }

    public void tearDown(NoSqlUnitTestContext context) {

        DataSetExecutor executor = context.getDataSetExecutor();
        DataSet dataSet = context.getAnnotation(DataSet.class);

        if (dataSet != null) {
            DataSetConfig dataSetConfig = new DataSetConfig(dataSet);
            executor.clearStorage(dataSetConfig);
        }
    }
}
