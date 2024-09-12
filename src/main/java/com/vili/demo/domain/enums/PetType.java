package com.vili.demo.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PetType {

    CAT(1, "CAT"),
    DOG(2, "DOG");

    @JsonValue
    private final Integer id;
    private final String label;
}
