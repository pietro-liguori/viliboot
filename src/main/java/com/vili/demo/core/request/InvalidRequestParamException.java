package com.vili.demo.core.request;

import lombok.Getter;

import java.io.Serial;
import java.util.List;

@Getter
public class InvalidRequestParamException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final List<FieldMessage> errors;

    public InvalidRequestParamException(List<FieldMessage> errors) {
        super("Invalid request parameter");
        this.errors = errors;
    }
}