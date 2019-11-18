package io.github.zghurskyi.core.storage;

import io.github.zghurskyi.core.configuration.DataSetConfig;
import io.github.zghurskyi.core.configuration.SeedStrategy;
import io.github.zghurskyi.core.dataset.NoSqlDataSet;

public interface StorageClient {

    void execute(SeedStrategy seedStrategy, NoSqlDataSet dataSet);

    void clearStorage(DataSetConfig dataSetConfig);

    NoSqlDataSet loadStorage(DataSetConfig dataSetConfig);
}
