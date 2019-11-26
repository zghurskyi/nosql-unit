package io.github.zghurskyi.nosqlunit.api.storage;

import java.util.List;
import java.util.Map;

public interface StorageDataSet {

    void createStorageDataSet(String key, List<Map<String, Object>> values);

    Map<String, StorageCollection> getStorageDataSet();


}
