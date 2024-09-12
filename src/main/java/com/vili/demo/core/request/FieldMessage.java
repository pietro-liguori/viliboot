package com.vili.demo.core.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class FieldMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String field;
    private String message;
}
