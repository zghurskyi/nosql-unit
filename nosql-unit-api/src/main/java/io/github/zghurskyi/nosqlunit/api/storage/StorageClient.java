package io.github.zghurskyi.nosqlunit.api.storage;

import io.github.zghurskyi.nosqlunit.api.properties.DataSetProperties;
import io.github.zghurskyi.nosqlunit.api.properties.SeedStrategy;

public interface StorageClient {

    void seedDataSet(SeedStrategy seedStrategy, StorageDataSet dataSet);

    void clearStorage(DataSetProperties dataSetProperties);

    StorageDataSet loadStorage(DataSetProperties dataSetProperties);
}
