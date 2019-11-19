package io.github.zghurskyi.core.dataset;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YamlDataSet implements StorageDataSet {

    private final Map<String, Storage> storageByKey = new HashMap<>();

    public YamlDataSet(InputStream source) {
        Map<String, List<Map<String, Object>>> data = new Yaml().load(source);
        if (data != null) {
            for (Map.Entry<String, List<Map<String, Object>>> ent : data.entrySet()) {
                String key = ent.getKey();
                List<Map<String, Object>> values = ent.getValue();
                createStorageDataSet(key, values);
            }
        }
    }

    @Override
    public void createStorageDataSet(String key, List<Map<String, Object>> data) {
        Storage storage = new Storage(key);
        if (data != null) {
            for (Map<String, Object> values : data) {
                storage.addRow(values);
            }
        }
        storageByKey.put(key.toUpperCase(), storage);
    }

    @Override
    public Map<String, Storage> getStorageDataSet() {
        return storageByKey;
    }
}
