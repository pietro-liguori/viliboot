package com.vili.demo.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TutorType {

    ADULT(1, "ADULT"),
    CHILD(2, "CHILD");

    @JsonValue
    private final Integer id;
    private final String label;
}
