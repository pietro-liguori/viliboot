package com.vili.demo.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@Getter
@RequiredArgsConstructor
public enum DocumentType {

    CPF(1, "CPF", DocumentTypeValidation::cpf),
    RG(2, "RG", DocumentTypeValidation::rg),
    PASSPORT(3, "PASSPORT", DocumentTypeValidation::passport);

    @JsonValue
    private final Integer id;
    private final String label;
    private final Function<String, Boolean> validationCallback;

    private static class DocumentTypeValidation {
        public static boolean cpf(String value) {
            return true;
        }

        public static boolean rg(String value) {
            return true;
        }

        public static boolean passport(String value) {
            return true;
        }
    }
}
