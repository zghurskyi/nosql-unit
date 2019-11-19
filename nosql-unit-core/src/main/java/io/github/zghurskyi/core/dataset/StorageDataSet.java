package io.github.zghurskyi.core.dataset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface StorageDataSet {

    void createStorageDataSet(String key, List<Map<String, Object>> values);

    Map<String, Storage> getStorageDataSet();

    class Storage {
        final String key;

        final List<Map<String, Object>> data;

        Storage(String key) {
            this.key = key;
            this.data = new ArrayList<>();
        }

        void addRow(Map<String, Object> values) {
            data.add(convertMap(values));
        }

        Map<String, Object> convertMap(Map<String, Object> values) {
            Map<String, Object> row = new HashMap<>();
            values.forEach(row::put);
            return row;
        }
    }
}
