package io.github.zghurskyi.core.dataset;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YamlDataSet extends NoSqlDataSet {
    private static final Logger log = LoggerFactory.getLogger(YamlDataSet.class);


    public YamlDataSet(InputStream source) {
        @SuppressWarnings("unchecked")
        Map<String, List<Map<String, Object>>> data = (Map<String, List<Map<String, Object>>>) new Yaml().load(source);
        if (data != null) {
            for (Map.Entry<String, List<Map<String, Object>>> ent : data.entrySet()) {
                String tableName = ent.getKey();
                List<Map<String, Object>> rows = ent.getValue();
                log.debug("Table {}, rows: {}", tableName, rows);
            }
        }
    }
}
