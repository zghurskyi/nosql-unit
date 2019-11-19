package io.github.zghurskyi.aerospike;

import io.github.zghurskyi.core.configuration.DataSetConfig;
import io.github.zghurskyi.core.configuration.SeedStrategy;
import io.github.zghurskyi.core.dataset.StorageDataSet;
import io.github.zghurskyi.core.storage.StorageClient;

public class AerospikeStorageClient implements StorageClient {

    @Override
    public void seedDataSet(SeedStrategy seedStrategy, StorageDataSet dataSet) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clearStorage(DataSetConfig dataSetConfig) {
        throw new UnsupportedOperationException();
    }

    @Override
    public StorageDataSet loadStorage(DataSetConfig dataSetConfig) {
        throw new UnsupportedOperationException();
    }
}
