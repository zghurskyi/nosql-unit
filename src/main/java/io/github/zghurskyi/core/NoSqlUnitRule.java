package io.github.zghurskyi.core;

import io.github.zghurskyi.core.api.DataSetExecutor;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class NoSqlUnitRule implements TestRule {

    private DataSetExecutor executor;

    private NoSqlUnitRule() {
    }

    @Override
    public Statement apply(final Statement statement, final Description description) {
        return new Statement() {

            @Override
            public void evaluate() throws Throwable {

                NoSqlUnitTestContext noSqlUnitTestContext = new DefaultNoSqlUnitTestContext(executor, description);
                NoSqlUnitRunner noSqlUnitRunner = new NoSqlUnitRunner();

                try {
                    noSqlUnitRunner.runBeforeTest(noSqlUnitTestContext);
                    statement.evaluate();
                    noSqlUnitRunner.runAfterTest(noSqlUnitTestContext);
                } finally {
                    noSqlUnitRunner.tearDown(noSqlUnitTestContext);
                }
            }
        };
    }
}
