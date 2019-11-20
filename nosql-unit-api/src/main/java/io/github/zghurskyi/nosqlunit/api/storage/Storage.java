package io.github.zghurskyi.nosqlunit.api.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Storage {
    public final String collection;
    public final List<Map<String, Object>> data;

    public Storage(String collection) {
        this.collection = collection;
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