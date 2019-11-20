package io.github.zghurskyi.nosqlunit.api.properties;

public enum SeedStrategy {

    /**
     * Insert the data defined in provided data sets, after removal of all data present in the storage.
     */
    CLEAN_INSERT,
    /**
     * Insert the data defined in provided data sets.
     */
    INSERT,
    /**
     * Existing rows are updated and new ones are inserted.
     * Entries already existing in the database which are not defined in the provided data set are not affected.
     */
    REFRESH,
    /**
     * Update existing rows using data provided in the datasets.
     * If dataset contain a row which is not present in the storage (identified by its primary key) then exception is thrown.
     */
    UPDATE;
}
