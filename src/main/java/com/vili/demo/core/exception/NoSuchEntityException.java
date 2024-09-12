package com.vili.demo.core.exception;

import java.io.Serial;

public class NoSuchEntityException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public NoSuchEntityException() {
        super("No such element");
    }

    public NoSuchEntityException(String msg) {
        super(msg);
    }

}