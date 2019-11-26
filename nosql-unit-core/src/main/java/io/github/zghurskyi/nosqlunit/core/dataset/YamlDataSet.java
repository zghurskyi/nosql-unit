package io.github.zghurskyi.nosqlunit.core.dataset;

import io.github.zghurskyi.nosqlunit.api.storage.StorageCollection;
import io.github.zghurskyi.nosqlunit.api.storage.StorageDataSet;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YamlDataSet implements StorageDataSet {

    private final Map<String, StorageCollection> storageByNamespace = new HashMap<>();

    public YamlDataSet(InputStream source) {
        Map<String, List<Map<String, Object>>> data = new Yaml().load(source);
        if (data != null) {
            data.forEach(this::createStorageDataSet);
        }
    }

    @Override
    public void createStorageDataSet(String collection, List<Map<String, Object>> data) {
        StorageCollection storageCollection = new StorageCollection(collection);
        if (data != null) {
            data.forEach(storageCollection::addRow);
        }
        storageByNamespace.put(collection.toUpperCase(), storageCollection);
    }

    @Override
    public Map<String, StorageCollection> getStorageDataSet() {
        return storageByNamespace;
    }
}
