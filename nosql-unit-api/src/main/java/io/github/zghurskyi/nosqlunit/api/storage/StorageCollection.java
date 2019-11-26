package io.github.zghurskyi.nosqlunit.api.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StorageCollection {
    public String name;
    public List<Map<String, Object>> data;

    public StorageCollection() {
    }

    public StorageCollection(String name) {
        this.name = name;
        this.data = new ArrayList<>();
    }

    public void addRow(Map<String, Object> values) {
        data.add(convertMap(values));
    }

    public Map<String, Object> convertMap(Map<String, Object> values) {
        Map<String, Object> row = new HashMap<>();
        values.forEach(row::put);
        return row;
    }
}