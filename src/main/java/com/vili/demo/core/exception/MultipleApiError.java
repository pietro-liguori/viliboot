package com.vili.demo.core.exception;

import java.io.Serial;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vili.demo.core.request.FieldMessage;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data @EqualsAndHashCode(callSuper = true) @NoArgsConstructor
@JsonIgnoreProperties(value = { "message" })
public class MultipleApiError extends SingleApiError {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<FieldMessage> errors = new ArrayList<>();

    @Builder(builderMethodName = "multipleErrorBuilder")
    public MultipleApiError(Instant timestamp, Integer status, String name, String path, List<FieldMessage> messages) {
        super(timestamp, status, name, null, path);
    }

    public void add(String fieldName, String message) {
        errors.add(new FieldMessage(fieldName, message));
    }

    public void add(FieldMessage message) {
        errors.add(message);
    }

}
