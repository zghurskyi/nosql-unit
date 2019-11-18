package io.github.zghurskyi.core.api;

import io.github.zghurskyi.core.configuration.DataSetConfig;
import io.github.zghurskyi.core.configuration.NoSqlUnitConfig;

public interface DataSetExecutor {

    void createDataSet(DataSetConfig dataSetConfig);

    void compareCurrentDataSetWith(DataSetConfig dataSetConfig);

    void clearStorage(DataSetConfig dataSetConfig);
}
