package io.github.zghurskyi.nosqlunit.core.aerospike;

import io.github.zghurskyi.nosqlunit.aerospike.AerospikeStorageClientBuilder;
import io.github.zghurskyi.nosqlunit.api.annotation.ExpectedDataSet;
import io.github.zghurskyi.nosqlunit.api.annotation.InitialDataSet;
import io.github.zghurskyi.nosqlunit.api.rule.NoSqlUnitRule;
import io.github.zghurskyi.nosqlunit.core.executor.DefaultDataSetExecutor;
import org.junit.Rule;
import org.junit.Test;

public class AerospikeStorageTest {

    @Rule
    public NoSqlUnitRule noSqlUnitRule = NoSqlUnitRule.NoSqlUnitRuleBuilder.instance()
            .executor(DefaultDataSetExecutor.DataSetExecutorBuilder.instance()
                    .storageClient(AerospikeStorageClientBuilder.instance())
                    .build())
            .build();

    @Test
    @InitialDataSet("initial-users.yml")
    @ExpectedDataSet("expected-users.yml")
    public void storageTestSuccess() {

    }

    @Test
    @InitialDataSet("initial-users.yml")
    @ExpectedDataSet("empty-users.yml")
    public void storageTestFailure() {

    }
}