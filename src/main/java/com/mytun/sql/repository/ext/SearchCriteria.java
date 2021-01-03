package com.mytun.sql.repository.ext;

public class SearchCriteria {
    private String key;
    private String operation;
    private String value;

    public String getKey() {
        return key;
    }

    public String getOperation() {
        return operation;
    }

    public String getValue() {
        return value;
    }

    public SearchCriteria(String query) {
        String[] v = query.split("\\$");
        this.key = v[0];
        this.operation = v[1];
        if(v.length==3){
            this.value = v[2];
        }
        if(v.length>3){
            throw new RuntimeException("参数出错，长度大于3，出错字段："+query);
        }
    }
}
