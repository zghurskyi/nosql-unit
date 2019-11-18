package io.github.zghurskyi.core.dataset;

import io.github.zghurskyi.core.api.DataSetExecutor;
import io.github.zghurskyi.core.assertion.DataSetAssertion;
import io.github.zghurskyi.core.configuration.DataSetConfig;
import io.github.zghurskyi.core.exception.DataSetSetupException;
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
                    NoSqlDataSet loadedDataSet = loadDataSet(dataSetConfig.getDataSet());
                    storageClient.execute(dataSetConfig.getSeedStrategy(), loadedDataSet);
                } else {
                    log.warn("Could not initialize storage: no dataSet has been provided.");
                }
            } catch (Throwable throwable) {
                String message = String.format("Could not initialize dataSet: %s", dataSetConfig);
                throw new DataSetSetupException(message, throwable);
            }
        }
    }

    @Override
    public void compareCurrentDataSetWith(DataSetConfig dataSetConfig) {

        NoSqlDataSet current = storageClient.loadStorage(dataSetConfig);
        NoSqlDataSet expected = loadDataSet(dataSetConfig.getDataSet());

        DataSetAssertion.assertEquals(current, expected);
    }

    @Override
    public void clearStorage(DataSetConfig dataSetConfig) {
        storageClient.clearStorage(dataSetConfig);
    }

    private NoSqlDataSet loadDataSet(String dataSet) {

        String dataSetName = dataSet.trim();
        String extension = dataSetName.substring(dataSetName.lastIndexOf('.') + 1).toLowerCase();

        if ("yml".equals(extension)) {
            return new YamlDataSet(getDataSetStream(dataSetName));
        } else {
            String message = String.format("No dataSet loaded for name '%s': unsupported dataSet extension %s", dataSet, extension);
            log.error(message);
            throw new RuntimeException(message);
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
            throw new RuntimeException(message);
        }
        return is;
    }
}
