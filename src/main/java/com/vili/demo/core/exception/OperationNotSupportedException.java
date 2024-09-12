package com.vili.demo.core.exception;

import com.vili.demo.core.request.QueryOperator;

import java.io.Serial;

public class OperationNotSupportedException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public OperationNotSupportedException(QueryOperator op) {
        super("Operation not supported yet: " + op);
    }
}