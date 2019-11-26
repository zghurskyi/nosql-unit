package io.github.zghurskyi.nosqlunit.core.executor;

import io.github.zghurskyi.nosqlunit.api.executor.DataSetExecutor;
import io.github.zghurskyi.nosqlunit.api.properties.DataSetProperties;
import io.github.zghurskyi.nosqlunit.api.storage.StorageClient;
import io.github.zghurskyi.nosqlunit.api.storage.StorageDataSet;
import io.github.zghurskyi.nosqlunit.core.assertion.DataSetAssertion;
import io.github.zghurskyi.nosqlunit.core.dataset.YamlDataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public class DefaultDataSetExecutor implements DataSetExecutor {

    private static final Logger log = LoggerFactory.getLogger(DefaultDataSetExecutor.class);

    private final StorageClient storageClient;

    private DefaultDataSetExecutor(StorageClient storageClient) {
        this.storageClient = storageClient;
    }

    @Override
    public void createDataSet(DataSetProperties dataSetProperties) {

        if (dataSetProperties != null) {
            try {
                if (dataSetProperties.hasDataSets()) {
                    StorageDataSet loadedDataSet = loadDataSet(dataSetProperties.getDataSet());
                    storageClient.seedDataSet(dataSetProperties.getSeedStrategy(), loadedDataSet);
                } else {
                    log.warn("Could not initialize storage: no dataSet has been provided.");
                }
            } catch (Throwable throwable) {
                String message = String.format("Could not initialize dataSet: %s", dataSetProperties);
                throw new IllegalStateException(message, throwable);
            }
        }
    }

    @Override
    public void compareCurrentWithExpectedDataSet(DataSetProperties dataSetProperties) {

        StorageDataSet current = storageClient.loadStorage(dataSetProperties);
        StorageDataSet expected = loadDataSet(dataSetProperties.getDataSet());

        DataSetAssertion.assertEquals(current, expected);
    }

    @Override
    public void clearStorage(DataSetProperties dataSetProperties) {
        storageClient.clearStorage(dataSetProperties);
    }

    private StorageDataSet loadDataSet(String dataSet) {

        String dataSetName = dataSet.trim();
        String extension = dataSetName.substring(dataSetName.lastIndexOf('.') + 1).toLowerCase();

        if ("yml".equals(extension)) {
            return new YamlDataSet(getDataSetStream(dataSetName));
        } else {
            String message = String.format("No dataSet loaded for name '%s': unsupported dataSet extension %s", dataSet, extension);
            log.error(message);
            throw new IllegalStateException(message);
        }
    }

    private InputStream getDataSetStream(String dataSet) {
        if (!dataSet.startsWith("/")) {
            dataSet = "/" + dataSet;
        }
        InputStream is = getClass().getResourceAsStream(dataSet);
        if (is == null) {
            is = getClass().getResourceAsStream("/datasets" + dataSet);
        }
        if (is == null) {
            String message = String.format("Could not find dataSet '%s' under 'resources' or 'resources/datasets' directory.", dataSet.substring(1));
            throw new IllegalStateException(message);
        }
        return is;
    }

    public static class DataSetExecutorBuilder {

        private StorageClient client;

        private DataSetExecutorBuilder() {
        }

        public static DataSetExecutorBuilder instance() {
            return new DataSetExecutorBuilder();
        }

        public DataSetExecutorBuilder storageClient(StorageClient storageConfig) {
            this.client = storageConfig;
            return this;
        }

        public DataSetExecutor build() {
            return new DefaultDataSetExecutor(this.client);
        }
    }
}
