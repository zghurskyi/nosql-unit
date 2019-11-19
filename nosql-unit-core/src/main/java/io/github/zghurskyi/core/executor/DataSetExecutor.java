package io.github.zghurskyi.core.executor;

import io.github.zghurskyi.core.configuration.DataSetConfig;

public interface DataSetExecutor {

    void createDataSet(DataSetConfig dataSetConfig);

    void compareCurrentWithExpectedDataSet(DataSetConfig dataSetConfig);

    void clearStorage(DataSetConfig dataSetConfig);
}
