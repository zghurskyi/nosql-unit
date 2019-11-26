package io.github.zghurskyi.nosqlunit.core;

import io.github.zghurskyi.nosqlunit.api.annotation.ExpectedDataSet;
import io.github.zghurskyi.nosqlunit.api.annotation.InitialDataSet;
import io.github.zghurskyi.nosqlunit.api.rule.NoSqlUnitRule;
import io.github.zghurskyi.nosqlunit.core.executor.DefaultDataSetExecutor;
import io.github.zghurskyi.nosqlunit.core.teststorage.YamlStorageClient;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class NoSqlUnitRuleTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Rule
    public NoSqlUnitRule noSqlUnitRule = NoSqlUnitRule.NoSqlUnitRuleBuilder.instance()
            .executor(DefaultDataSetExecutor.DataSetExecutorBuilder.instance()
                    .storageClient(new YamlStorageClient(temporaryFolder))
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