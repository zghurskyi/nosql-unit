package io.github.zghurskyi.nosqlunit.api.rule;

import io.github.zghurskyi.nosqlunit.api.executor.DataSetExecutor;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class NoSqlUnitRule implements TestRule {

    private final DataSetExecutor executor;

    private NoSqlUnitRule(DataSetExecutor executor) {
        this.executor = executor;
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

    public static class NoSqlUnitRuleBuilder {

        private DataSetExecutor executor;

        private NoSqlUnitRuleBuilder() {
        }

        public static NoSqlUnitRuleBuilder instance() {
            return new NoSqlUnitRuleBuilder();
        }

        public NoSqlUnitRuleBuilder executor(DataSetExecutor executor) {
            this.executor = executor;
            return this;
        }

        public NoSqlUnitRule build() {
            return new NoSqlUnitRule(executor);
        }
    }
}
