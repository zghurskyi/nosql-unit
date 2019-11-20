package io.github.zghurskyi.nosqlunit.core.dataset;

import io.github.zghurskyi.nosqlunit.api.storage.Storage;
import io.github.zghurskyi.nosqlunit.api.storage.StorageDataSet;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YamlDataSet implements StorageDataSet {

    private final Map<String, Storage> storageByNamespace = new HashMap<>();

    public YamlDataSet(InputStream source) {
        Map<String, List<Map<String, Object>>> data = new Yaml().load(source);
        if (data != null) {
            data.forEach(this::createStorageDataSet);
        }
    }

    @Override
    public void createStorageDataSet(String collection, List<Map<String, Object>> data) {
        Storage storage = new Storage(collection);
        if (data != null) {
            data.forEach(storage::addRow);
        }
        storageByNamespace.put(collection.toUpperCase(), storage);
    }

    @Override
    public Map<String, Storage> getStorageDataSet() {
        return storageByNamespace;
    }
}
