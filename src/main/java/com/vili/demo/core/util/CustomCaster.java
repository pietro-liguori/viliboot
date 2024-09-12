package com.vili.demo.core.util;

import com.vili.demo.core.exception.CastingException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class CustomCaster {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Object castTo(Class dataType, String value) {
        if (dataType == null)
            return null;

        try {
            if (dataType.isAssignableFrom(Set.class)) {
                return Set.of(value);
            }

            if (dataType.isAssignableFrom(List.class)) {
                return List.of(value);
            }

            if (dataType.isAssignableFrom(Boolean.class)) {
                return Boolean.valueOf(value);
            }

            if (dataType.isAssignableFrom(Character.class)) {
                return value.charAt(0);
            }

            if (dataType.isAssignableFrom(Double.class)) {
                return Double.valueOf(value);
            }

            if (Enum.class.isAssignableFrom(dataType)) {
                return Enum.valueOf(dataType, value);
            }

            if (dataType.isAssignableFrom(Date.class)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                return sdf.parse(value);
            }

            if (dataType.isAssignableFrom(Integer.class)) {
                return Integer.valueOf(value);
            }

            if (dataType.isAssignableFrom(Long.class)) {
                return Long.valueOf(value);
            }

            if (dataType.isAssignableFrom(String.class)) {
                return value;
            }
        } catch (Exception e) {
            throw new CastingException("Cannot parse '" + value + "' to " + dataType.getSimpleName());
        }

        return null;
    }

    @SuppressWarnings({ "rawtypes" })
    public static List<Object> castTo(Class dataType, List<String> values) {
        List<Object> lists = new ArrayList<>();
        for (String value : values) {
            lists.add(castTo(dataType, value));
        }
        return lists;
    }

}