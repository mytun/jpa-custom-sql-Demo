package com.mytun.sql.repository.ext;

public class SearchCriteria {
    private String key;
    private String operation;
    private String value;
    private boolean variable = false;

    public String getKey() {
        return key;
    }

    public String getOperation() {
        return operation;
    }

    public String getValue() {
        return value;
    }

    public boolean isVariable() {
        return variable;
    }

    public SearchCriteria(String query) {
        String[] v = query.split("\\$");
        if(v.length>3){
            throw new RuntimeException("参数出错，长度大于3，出错字段："+query);
        }
        this.key = v[0];
        this.operation = v[1];
        if(v.length==3) {
            this.value = v[2];
            if (this.value.length() >= 2 &&
                    "'".equals(this.value.substring(0, 1)) &&
                    "'".equals(this.value.substring(this.value.length() - 1))) {
                this.variable = true;
                this.value = this.value.substring(1,this.value.length()-1);
            }
        }
    }
}
