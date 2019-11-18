package io.github.zghurskyi.core.configuration;

public class NoSqlUnitConfig {

    private ConnectionConfig connectionConfig;

    public NoSqlUnitConfig() {
    }

    private void initDefault() {
        initDefaultConnectionConfig();
    }

    private void initDefaultConnectionConfig() {
        if (connectionConfig == null) {
            connectionConfig = new ConnectionConfig();
        }

        if (connectionConfig.getDriver() == null) {
            connectionConfig.setDriver("");
        }

        if (connectionConfig.getUrl() == null) {
            connectionConfig.setUrl("");
        }

        if (connectionConfig.getUser() == null) {
            connectionConfig.setUser("");
        }

        if (connectionConfig.getPassword() == null) {
            connectionConfig.setPassword("");
        }
    }

    public ConnectionConfig getConnectionConfig() {
        return connectionConfig;
    }

    public void setConnectionConfig(ConnectionConfig connectionConfig) {
        this.connectionConfig = connectionConfig;
    }


}
