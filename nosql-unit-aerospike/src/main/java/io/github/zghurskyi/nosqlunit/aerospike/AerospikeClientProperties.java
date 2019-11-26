package io.github.zghurskyi.nosqlunit.aerospike;

import com.aerospike.client.Log;

public class AerospikeClientProperties {
    private String hosts = "localhost";
    private int defaultPort = 3000;
    private String namespace = "TEST";
    private String user;
    private String password;
    private String clusterName;
    private int eventLoopsSize = getDefaultEventLoopsSize();
    private int maxConnectionsPerNode = 300;
    private int connectionPoolsPerNode = 1;
    private int maxSocketIdleSec = 55;
    private int tendIntervalMs = 1_000;
    private int connectTimeoutMs = 5_000;
    private int operationTimeoutMs = 200;
    private int maxOperationRetries = 1;
    private boolean sendKey = false;
    private Log.Level logLevel = Log.Level.INFO;

    public AerospikeClientProperties() {
    }

    public String getHosts() {
        return hosts;
    }

    public void setHosts(String hosts) {
        this.hosts = hosts;
    }

    public int getDefaultPort() {
        return defaultPort;
    }

    public void setDefaultPort(int defaultPort) {
        this.defaultPort = defaultPort;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public int getEventLoopsSize() {
        return eventLoopsSize;
    }

    public void setEventLoopsSize(int eventLoopsSize) {
        this.eventLoopsSize = eventLoopsSize;
    }

    public int getMaxConnectionsPerNode() {
        return maxConnectionsPerNode;
    }

    public void setMaxConnectionsPerNode(int maxConnectionsPerNode) {
        this.maxConnectionsPerNode = maxConnectionsPerNode;
    }

    public int getConnectionPoolsPerNode() {
        return connectionPoolsPerNode;
    }

    public void setConnectionPoolsPerNode(int connectionPoolsPerNode) {
        this.connectionPoolsPerNode = connectionPoolsPerNode;
    }

    public int getMaxSocketIdleSec() {
        return maxSocketIdleSec;
    }

    public void setMaxSocketIdleSec(int maxSocketIdleSec) {
        this.maxSocketIdleSec = maxSocketIdleSec;
    }

    public int getTendIntervalMs() {
        return tendIntervalMs;
    }

    public void setTendIntervalMs(int tendIntervalMs) {
        this.tendIntervalMs = tendIntervalMs;
    }

    public int getConnectTimeoutMs() {
        return connectTimeoutMs;
    }

    public void setConnectTimeoutMs(int connectTimeoutMs) {
        this.connectTimeoutMs = connectTimeoutMs;
    }

    public int getOperationTimeoutMs() {
        return operationTimeoutMs;
    }

    public void setOperationTimeoutMs(int operationTimeoutMs) {
        this.operationTimeoutMs = operationTimeoutMs;
    }

    public boolean isSendKey() {
        return sendKey;
    }

    public void setSendKey(boolean sendKey) {
        this.sendKey = sendKey;
    }

    public Log.Level getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Log.Level logLevel) {
        this.logLevel = logLevel;
    }

    public int getMaxOperationRetries() {
        return maxOperationRetries;
    }

    public void setMaxOperationRetries(int maxOperationRetries) {
        this.maxOperationRetries = maxOperationRetries;
    }

    private static int getDefaultEventLoopsSize() {
        try {
            return Runtime.getRuntime().availableProcessors();
        } catch (Exception e) {
            return 1;
        }
    }
}
