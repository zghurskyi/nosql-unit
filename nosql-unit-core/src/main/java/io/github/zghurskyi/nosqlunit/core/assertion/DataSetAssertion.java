package io.github.zghurskyi.nosqlunit.core.assertion;

import io.github.zghurskyi.nosqlunit.api.storage.StorageCollection;
import io.github.zghurskyi.nosqlunit.api.storage.StorageDataSet;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DataSetAssertion {

    public static void assertEquals(StorageDataSet current, StorageDataSet expected) {
        Map<String, StorageCollection> actualDataSet = current.getStorageDataSet();
        Map<String, StorageCollection> expectedDataSet = expected.getStorageDataSet();
        if (actualDataSet.size() != expectedDataSet.size()) {
            throw new AssertionError("Data sets have different size");
        }
        for (String namespace : expectedDataSet.keySet()) {
            StorageCollection expectedStorageCollection = expectedDataSet.get(namespace);
            StorageCollection actualStorageCollection = actualDataSet.get(namespace);
            if (actualStorageCollection == null && expectedStorageCollection == null) {
                return;
            }
            if (actualStorageCollection == null) {
                throw new AssertionError("Actual is null");
            }
            if (expectedStorageCollection == null) {
                throw new AssertionError("Expected is null");
            }
            if (!Objects.equals(actualStorageCollection.name.toUpperCase(), expectedStorageCollection.name.toUpperCase())) {
                throw new AssertionError("Expected and actual names are different");
            }
            List<Map<String, Object>> actualRows = actualStorageCollection.data;
            List<Map<String, Object>> expectedRows = expectedStorageCollection.data;
            if (actualRows.size() != expectedRows.size()) {
                throw new AssertionError("Different rows count");
            }
            for (int i = 0; i < expectedRows.size(); i++) {
                Map<String, Object> expectedRow = expectedRows.get(i);
                Map<String, Object> actualRow = actualRows.get(i);
                for (String column : expectedRow.keySet()) {
                    if (!actualRow.containsKey(column)) {
                        throw new AssertionError("Missing column");
                    }
                    Object expectedValue = expectedRow.get(column);
                    Object actualValue = actualRow.get(column);
                    if (!expectedValue.equals(actualValue)) {
                        throw new AssertionError("Different column values");
                    }
                }
            }
        }
    }
}
