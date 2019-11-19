package io.github.zghurskyi.core.storage;

import io.github.zghurskyi.core.configuration.DataSetConfig;
import io.github.zghurskyi.core.configuration.SeedStrategy;
import io.github.zghurskyi.core.dataset.StorageDataSet;

public interface StorageClient {

    void seedDataSet(SeedStrategy seedStrategy, StorageDataSet dataSet);

    void clearStorage(DataSetConfig dataSetConfig);

    StorageDataSet loadStorage(DataSetConfig dataSetConfig);
}
