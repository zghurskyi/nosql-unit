package io.github.zghurskyi.core.configuration;

import io.github.zghurskyi.core.api.DataSet;

import java.util.Objects;

public class DataSetConfig {

    private SeedStrategy seedStrategy = SeedStrategy.CLEAN_INSERT;
    private String dataSet;

    public DataSetConfig() {
    }

    public DataSetConfig(DataSet dataSet) {
        this(dataSet.value());
    }

    public DataSetConfig(String dataSet) {
        this.dataSet = Objects.requireNonNull(dataSet, "DataSetConfig can't null");
    }

    public boolean hasDataSets() {
        return dataSet != null && !dataSet.trim().isEmpty();
    }

    public String getDataSet() {
        return dataSet;
    }

    public SeedStrategy getSeedStrategy() {
        return seedStrategy;
    }
}
