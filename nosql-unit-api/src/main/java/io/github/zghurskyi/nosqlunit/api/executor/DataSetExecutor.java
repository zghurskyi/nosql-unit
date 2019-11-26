package io.github.zghurskyi.nosqlunit.api.executor;

import io.github.zghurskyi.nosqlunit.api.properties.DataSetProperties;

public interface DataSetExecutor {

    void createDataSet(DataSetProperties dataSetProperties);

    void compareCurrentWithExpectedDataSet(DataSetProperties dataSetProperties);

    void clearStorage(DataSetProperties dataSetProperties);
}
