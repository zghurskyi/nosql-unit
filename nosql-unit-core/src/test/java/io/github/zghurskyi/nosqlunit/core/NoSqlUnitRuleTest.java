package io.github.zghurskyi.nosqlunit.core;

import io.github.zghurskyi.nosqlunit.aerospike.AerospikeStorageClient;
import io.github.zghurskyi.nosqlunit.api.annotation.ExpectedDataSet;
import io.github.zghurskyi.nosqlunit.api.annotation.InitialDataSet;
import io.github.zghurskyi.nosqlunit.core.executor.DefaultDataSetExecutor;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class NoSqlUnitRuleTest {

    @Rule
    public NoSqlUnitRule noSqlUnitRule = NoSqlUnitRule.NoSqlUnitRuleBuilder.instance()
            .executor(DefaultDataSetExecutor.DataSetExecutorBuilder.instance()
                    .storageClient(AerospikeStorageClient.AerospikeStorageClientBuilder.instance()
                            .build())
                    .build())
            .build();

    @Test
    @InitialDataSet
    @ExpectedDataSet
    public void storageTest() {

    }
}