package com.vili.demo.core.request;

import java.util.List;

public class Filter {

    private String fieldName;
    private Class<?> fieldType;
    private QueryOperator operator;
    private String value;
    private List<String> values;

    private Filter() {

    }

    public String fieldName() {
        return fieldName;
    }

    public Filter fieldName(String field) {
        this.fieldName = field;
        return this;
    }

    public QueryOperator operator() {
        return operator;
    }

    public Filter operator(QueryOperator operator) {
        this.operator = operator;
        return this;
    }

    public String value() {
        return value;
    }

    public Filter value(String value) {
        this.value = value;
        return this;
    }

    public List<String> values() {
        return values;
    }

    public Filter values(List<String> values) {
        this.values = values;
        return this;
    }

    public Class<?> fieldType() {
        return fieldType;
    }

    public Filter fieldType(Class<?> fieldType) {
        this.fieldType = fieldType;
        return this;
    }

    public static Filter builder() {
        return new Filter();
    }
}
