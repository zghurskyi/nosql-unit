package io.github.zghurskyi.nosqlunit.api.properties;

import io.github.zghurskyi.nosqlunit.api.annotation.InitialDataSet;

import java.util.Objects;

public class DataSetProperties {

    private SeedStrategy seedStrategy = SeedStrategy.CLEAN_INSERT;
    private String dataSet;

    public DataSetProperties() {
    }

    public DataSetProperties(InitialDataSet initialDataSet) {
        this(initialDataSet.value());
    }

    public DataSetProperties(String dataSet) {
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
