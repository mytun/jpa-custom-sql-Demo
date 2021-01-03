package com.mytun.sql.repository.ext;

public class OracleCriteria {
    private String key;
    private String operation;

    public String getKey() {
        return key;
    }

    public String getOperation() {
        return operation;
    }

    public OracleCriteria(String query) {
        String[] v = query.split("\\$");
        this.key = v[0];
        this.operation = v[1];
    }
}
