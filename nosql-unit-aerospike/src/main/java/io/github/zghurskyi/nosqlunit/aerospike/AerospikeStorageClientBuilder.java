package io.github.zghurskyi.nosqlunit.aerospike;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Host;
import com.aerospike.client.Log;
import com.aerospike.client.async.EventLoops;
import com.aerospike.client.async.EventPolicy;
import com.aerospike.client.async.NettyEventLoops;
import com.aerospike.client.policy.ClientPolicy;
import com.aerospike.client.policy.GenerationPolicy;
import io.github.zghurskyi.nosqlunit.api.storage.StorageClient;
import io.netty.channel.nio.NioEventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;

public class AerospikeStorageClientBuilder {

    private static final Logger log = LoggerFactory.getLogger(AerospikeStorageClientBuilder.class);

    private AerospikeStorageClientBuilder() {
    }

    public static StorageClient instance() {

        AerospikeClientProperties clientProperties = new AerospikeClientProperties();
        EventLoops eventLoops = eventLoops(clientProperties);
        AerospikeClient aerospikeClient = aerospikeClient(clientProperties, eventLoops);

        return new AerospikeStorageClient(aerospikeClient);
    }

    private static EventLoops eventLoops(AerospikeClientProperties aerospikeProperties) {
        return new NettyEventLoops(new EventPolicy(), new NioEventLoopGroup(aerospikeProperties.getEventLoopsSize()));
    }

    private static AerospikeClient aerospikeClient(AerospikeClientProperties aerospikeProperties, EventLoops eventLoops) {
        aerospikeLogging(aerospikeProperties);
        Collection<Host> hosts = getHosts(aerospikeProperties);
        return new AerospikeClient(getClientPolicy(aerospikeProperties, eventLoops), hosts.toArray(new Host[0]));
    }

    private static Collection<Host> getHosts(AerospikeClientProperties aerospikeProperties) {
        String hostsString = aerospikeProperties.getHosts();
        Host[] hosts = Host.parseHosts(hostsString, aerospikeProperties.getDefaultPort());
        return Arrays.asList(hosts);
    }

    private static ClientPolicy getClientPolicy(AerospikeClientProperties aerospikeProperties, EventLoops eventLoops) {

        ClientPolicy clientPolicy = new ClientPolicy();

        clientPolicy.failIfNotConnected = true;
        clientPolicy.timeout = aerospikeProperties.getConnectTimeoutMs();
        clientPolicy.user = aerospikeProperties.getUser();
        clientPolicy.password = aerospikeProperties.getPassword();
        clientPolicy.clusterName = aerospikeProperties.getClusterName();
        clientPolicy.tendInterval = aerospikeProperties.getTendIntervalMs();
        clientPolicy.eventLoops = eventLoops;

        clientPolicy.writePolicyDefault.totalTimeout = aerospikeProperties.getOperationTimeoutMs();
        clientPolicy.writePolicyDefault.socketTimeout = aerospikeProperties.getOperationTimeoutMs();
        clientPolicy.writePolicyDefault.maxRetries = aerospikeProperties.getMaxOperationRetries();
        clientPolicy.writePolicyDefault.generationPolicy = GenerationPolicy.EXPECT_GEN_EQUAL;
        clientPolicy.writePolicyDefault.sendKey = aerospikeProperties.isSendKey();

        clientPolicy.readPolicyDefault.totalTimeout = aerospikeProperties.getOperationTimeoutMs();
        clientPolicy.readPolicyDefault.socketTimeout = aerospikeProperties.getOperationTimeoutMs();
        clientPolicy.readPolicyDefault.maxRetries = aerospikeProperties.getMaxOperationRetries();

        return clientPolicy;
    }

    private static void aerospikeLogging(AerospikeClientProperties aerospikeProperties) {
        Log.setLevel(aerospikeProperties.getLogLevel());
        Log.setCallback((level, message) -> {
            switch (level) {
                case DEBUG:
                    log.debug(message);
                    break;
                case INFO:
                    log.info(message);
                    break;
                case WARN:
                    log.warn(message);
                    break;
                case ERROR:
                    log.error(message);
                    break;
            }
        });
    }

}
