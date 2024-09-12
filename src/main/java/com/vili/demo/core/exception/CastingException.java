package com.vili.demo.core.exception;

import java.io.Serial;

public class CastingException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public CastingException(String msg) {
        super(msg);
    }
}