package com.vili.demo.core.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(Long id) {
        super("Resource not found [Id=" + id + "]");
    }

    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
