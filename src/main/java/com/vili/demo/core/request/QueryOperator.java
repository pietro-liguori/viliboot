package com.vili.demo.core.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.regex.Pattern;

@Getter
@RequiredArgsConstructor
public enum QueryOperator {

    EQUALS(1, null),
    NOT_EQUALS(2, Pattern.compile("^!")), // Example: !value
    GREATER_THAN(3, Pattern.compile("^>[^=]")), // Example: >date OR >number
    LESS_THAN(4, Pattern.compile("^<[^=]")), // Example: <date OR <number
    GREATER_OR_EQUAL(5, Pattern.compile("^>=")), // Example: >=date OR >=number
    LESS_OR_EQUAL(6, Pattern.compile("^<=")), // Example: <=date OR <=number
    BETWEEN(7, Pattern.compile("(^\\d{4}-\\d{2}-\\d{2}:\\d{4}-\\d{2}-\\d{2}$)|(^\\d+(.\\d+)?:\\d(.\\d+)?+$)")), // Example: date1:date2 OR number1:number2
    LIKE(8, Pattern.compile("^\\*.+\\*$")), // Example: *value value value*
    STARTS_WITH(9, Pattern.compile("^\\*\\w*[^*]$")), // Example: *value
    ENDS_WITH(10, Pattern.compile("^[^*]\\w*\\*$")), // Example: value*
    IN(11, Pattern.compile("^_[^,]+(,[^,]+)+_$")); // Example: _value1,value2,...,valueN_,

    private final int id;
    private final Pattern pattern;

    public static QueryOperator from(String value) {
        for (QueryOperator x : QueryOperator.values()) {
            if (x.getPattern() == null)
                continue;

            if (x.getPattern().matcher(value).find())
                return x;
        }

        return EQUALS;
    }

    public static Object treat(String value) {
        if (NOT_EQUALS.getPattern().matcher(value).find() || GREATER_THAN.getPattern().matcher(value).find() || LESS_THAN.getPattern().matcher(value).find() || STARTS_WITH.getPattern().matcher(value).find())
            return value.substring(1);

        if (GREATER_OR_EQUAL.getPattern().matcher(value).find() || LESS_OR_EQUAL.getPattern().matcher(value).find())
            return value.substring(2);

        if (ENDS_WITH.getPattern().matcher(value).find())
            return value.substring(0, value.length() - 1);

        if (LIKE.getPattern().matcher(value).find())
            return value.substring(1, value.length() - 1);

        if (IN.getPattern().matcher(value).find())
            return Arrays.asList(value.substring(1, value.length() - 1).split(","));

        if (BETWEEN.getPattern().matcher(value).find())
            return Arrays.asList(value.split(":")).subList(0, 2);

        return value;
    }
}
