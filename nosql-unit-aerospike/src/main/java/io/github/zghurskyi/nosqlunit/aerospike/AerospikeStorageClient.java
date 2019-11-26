package io.github.zghurskyi.nosqlunit.aerospike;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.AerospikeException;
import com.aerospike.client.Bin;
import com.aerospike.client.Host;
import com.aerospike.client.IAerospikeClient;
import com.aerospike.client.Info;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.ScanCallback;
import com.aerospike.client.policy.ClientPolicy;
import io.github.zghurskyi.nosqlunit.api.properties.DataSetProperties;
import io.github.zghurskyi.nosqlunit.api.properties.SeedStrategy;
import io.github.zghurskyi.nosqlunit.api.storage.StorageClient;
import io.github.zghurskyi.nosqlunit.api.storage.StorageCollection;
import io.github.zghurskyi.nosqlunit.api.storage.StorageDataSet;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class AerospikeStorageClient implements StorageClient {

    private static final String TEST = "TEST";
    private final AerospikeClient aerospikeClient;

    AerospikeStorageClient(AerospikeClient aerospikeClient) {
        this.aerospikeClient = aerospikeClient;
    }

    @Override
    public void seedDataSet(SeedStrategy seedStrategy, StorageDataSet dataSet) {
        Map<String, StorageCollection> storageDataSet = dataSet.getStorageDataSet();
        for (Map.Entry<String, StorageCollection> entry : storageDataSet.entrySet()) {
            StorageCollection collection = entry.getValue();
            for (Map<String, Object> row : collection.data) {
                Map.Entry<String, Object> rowKey = row.entrySet().iterator().next();
                Key key = new Key(TEST, collection.name, rowKey.getKey());
                for (Map.Entry<String, Object> bins : row.entrySet()) {
                    Bin bin = new Bin(bins.getKey(), bins.getValue());
                    aerospikeClient.put(null, key, bin);
                }
            }
        }
    }

    @Override
    public void clearStorage(DataSetProperties dataSetProperties) {
        while(!isEmptyNamespace(aerospikeClient, TEST)){
            aerospikeClient.truncate(null, TEST, null, null);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
        }
    }

    @Override
    public StorageDataSet loadStorage(DataSetProperties dataSetProperties) {

        aerospikeClient.scanAll(null, TEST, "test", (key, record) -> {
            System.out.println("Key: " + key + ". Record: " + record);
        });
        // todo
        return null;
    }

    private static boolean isEmptyNamespace(IAerospikeClient client, String namespace){
        String answer = Info.request(client.getNodes()[0], "sets/" + namespace);
        return answer.isEmpty() || Stream.of(answer.split(";")).allMatch(s -> s.contains("objects=0"));
    }
}
