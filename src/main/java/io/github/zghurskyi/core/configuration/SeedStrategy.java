package io.github.zghurskyi.core.configuration;

/**
 * <p>
 * INSERT
 * Performs insert of the data defined in provided data sets. This is the default strategy.
 * <p>
 * CLEAN_INSERT
 * Performs insert of the data defined in provided data sets, after removal of all data present in the tables (DELETE_ALL invoked by DBUnit before INSERT).
 * <p>
 * REFRESH
 * During this operation existing rows are updated and new ones are inserted. Entries already existing in the database which are not defined in the provided data set are not affected.
 * <p>
 * UPDATE
 * This strategy updates existing rows using data provided in the datasets. If dataset contain a row which is not present in the database (identified by its primary key) then exception is thrown.
 */
public enum SeedStrategy {
    CLEAN_INSERT,
    INSERT,
    REFRESH,
    UPDATE;
}
