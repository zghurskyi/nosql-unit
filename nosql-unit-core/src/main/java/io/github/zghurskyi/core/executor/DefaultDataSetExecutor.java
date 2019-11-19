package io.github.zghurskyi.core.executor;

import io.github.zghurskyi.core.assertion.DataSetAssertion;
import io.github.zghurskyi.core.configuration.DataSetConfig;
import io.github.zghurskyi.core.dataset.StorageDataSet;
import io.github.zghurskyi.core.dataset.YamlDataSet;
import io.github.zghurskyi.core.storage.StorageClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public class DefaultDataSetExecutor implements DataSetExecutor {

    private static final Logger log = LoggerFactory.getLogger(DefaultDataSetExecutor.class);

    private final StorageClient storageClient;

    public DefaultDataSetExecutor(StorageClient storageClient) {
        this.storageClient = storageClient;
    }

    @Override
    public void createDataSet(DataSetConfig dataSetConfig) {

        if (dataSetConfig != null) {
            try {
                if (dataSetConfig.hasDataSets()) {
                    StorageDataSet loadedDataSet = loadDataSet(dataSetConfig.getDataSet());
                    storageClient.seedDataSet(dataSetConfig.getSeedStrategy(), loadedDataSet);
                } else {
                    log.warn("Could not initialize storage: no dataSet has been provided.");
                }
            } catch (Throwable throwable) {
                String message = String.format("Could not initialize dataSet: %s", dataSetConfig);
                throw new IllegalStateException(message, throwable);
            }
        }
    }

    @Override
    public void compareCurrentWithExpectedDataSet(DataSetConfig dataSetConfig) {

        StorageDataSet current = storageClient.loadStorage(dataSetConfig);
        StorageDataSet expected = loadDataSet(dataSetConfig.getDataSet());

        DataSetAssertion.assertEquals(current, expected);
    }

    @Override
    public void clearStorage(DataSetConfig dataSetConfig) {
        storageClient.clearStorage(dataSetConfig);
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
}
