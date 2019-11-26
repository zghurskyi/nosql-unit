package io.github.zghurskyi.nosqlunit.core.teststorage;

import io.github.zghurskyi.nosqlunit.api.properties.DataSetProperties;
import io.github.zghurskyi.nosqlunit.api.properties.SeedStrategy;
import io.github.zghurskyi.nosqlunit.api.storage.StorageClient;
import io.github.zghurskyi.nosqlunit.api.storage.StorageCollection;
import io.github.zghurskyi.nosqlunit.api.storage.StorageDataSet;
import io.github.zghurskyi.nosqlunit.core.dataset.YamlDataSet;
import org.apache.commons.io.FileUtils;
import org.junit.rules.TemporaryFolder;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YamlStorageClient implements StorageClient {

    private final TemporaryFolder temporaryFolder;
    private File temporaryFile;

    public YamlStorageClient(TemporaryFolder temporaryFolder) {
        this.temporaryFolder = temporaryFolder;
    }

    @Override
    public void seedDataSet(SeedStrategy seedStrategy, StorageDataSet dataSet) {
        try {
            Map<String, StorageCollection> data = dataSet.getStorageDataSet();

            Map<String, List<Map<String, Object>>> dataMap = new HashMap<>();
            for (String namespace : data.keySet()) {
                dataMap.put(namespace, data.get(namespace).data);
            }

            Yaml yaml = new Yaml();
            StringWriter writer = new StringWriter();
            yaml.dump(dataMap, writer);

            temporaryFile = temporaryFolder.newFile("test-data.yml");
            FileUtils.write(temporaryFile, writer.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void clearStorage(DataSetProperties dataSetProperties) {
        // temporaryFolder is guaranteed to be deleted after test run
    }

    @Override
    public StorageDataSet loadStorage(DataSetProperties dataSetProperties) {
        try {
            return new YamlDataSet(FileUtils.openInputStream(temporaryFile));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
