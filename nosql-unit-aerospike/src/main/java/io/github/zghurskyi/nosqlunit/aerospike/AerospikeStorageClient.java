package io.github.zghurskyi.nosqlunit.aerospike;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Host;
import com.aerospike.client.policy.ClientPolicy;
import io.github.zghurskyi.nosqlunit.api.properties.DataSetProperties;
import io.github.zghurskyi.nosqlunit.api.properties.SeedStrategy;
import io.github.zghurskyi.nosqlunit.api.storage.StorageClient;
import io.github.zghurskyi.nosqlunit.api.storage.StorageDataSet;

public class AerospikeStorageClient implements StorageClient {

    private final AerospikeClient aerospikeClient;

    private AerospikeStorageClient(AerospikeClient aerospikeClient) {
        this.aerospikeClient = aerospikeClient;
    }

    @Override
    public void seedDataSet(SeedStrategy seedStrategy, StorageDataSet dataSet) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clearStorage(DataSetProperties dataSetProperties) {
        throw new UnsupportedOperationException();
    }

    @Override
    public StorageDataSet loadStorage(DataSetProperties dataSetProperties) {
        throw new UnsupportedOperationException();
    }

    public static class AerospikeStorageClientBuilder {

        private static final String LOCALHOST = "localhost";
        private static final int DEFAULT_PORT = 3000;

        AerospikeStorageClientBuilder() {
        }

        public static AerospikeStorageClientBuilder instance() {
            return new AerospikeStorageClientBuilder();
        }

        public StorageClient build() {
            ClientPolicy policy = new ClientPolicy();
            Host[] hosts = Host.parseHosts(LOCALHOST, DEFAULT_PORT);
            return new AerospikeStorageClient(new AerospikeClient(policy, hosts));
        }
    }
}
