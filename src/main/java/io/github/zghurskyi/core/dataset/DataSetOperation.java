package io.github.zghurskyi.core.dataset;

import io.github.zghurskyi.core.storage.StorageClient;

public interface DataSetOperation {
    void execute(StorageClient client, NoSqlDataSet dataSet);
}
