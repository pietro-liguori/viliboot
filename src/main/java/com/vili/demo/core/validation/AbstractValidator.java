package com.vili.demo.core.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import com.vili.demo.core.request.FieldMessage;
import jakarta.validation.ConstraintValidatorContext;

import lombok.Data;

@Data
public abstract class AbstractValidator {

    private ConstraintValidatorContext context;
    private List<FieldMessage> errors = new ArrayList<>();

    public void addError(FieldMessage error) {
        getErrors().add(error);
    }

    public void addErrors(List<FieldMessage> errors) {
        getErrors().addAll(errors);
    }

    public boolean buildConstraintViolations() {
        for (FieldMessage e : getErrors()) {
            getContext().disableDefaultConstraintViolation();
            getContext().buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getField()).addConstraintViolation();
        }

        return getErrors().isEmpty();
    }

    public void clear() {
        errors = new ArrayList<>();
    }

    public boolean after(String field, Date value, Date min, boolean required) {
        if (value == null) {
            if (required)
                addError(new FieldMessage(field, "Must not be null"));

            return false;
        } else {
            if (value.after(min)) {
                addError(new FieldMessage(field, "Must be after " + min));
                return false;
            }
        }

        return true;
    }

    public boolean before(String field, Date value, Date max, boolean required) {
        if (value == null) {
            if (required)
                addError(new FieldMessage(field, "Must not be null"));

            return false;
        } else {
            if (value.before(max)) {
                addError(new FieldMessage(field, "Must be before " + max));
                return false;
            }
        }

        return true;
    }

    public boolean email(String field, String value, boolean required) {
        String pattern  = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"; // RFC 5322

        if (value == null) {
            if (required)
                addError(new FieldMessage(field, "Must not be null"));

            return false;
        } else {
            if (!Pattern.compile(pattern).matcher(value).matches()) {
                addError(new FieldMessage(field, "Invalid email"));
                return false;
            }
        }

        return true;
    }

    public boolean future(String field, Date value, boolean required) {
        if (value == null) {
            if (required)
                addError(new FieldMessage(field, "Must not be null"));

            return false;
        } else {
            if (value.before(new Date())) {
                addError(new FieldMessage(field, "Must be future date"));
                return false;
            }
        }

        return true;
    }

    public boolean in(String field, Integer value, List<Integer> range, boolean required) {
        if (value == null) {
            if (required)
                addError(new FieldMessage(field, "Must not be null"));

            return false;
        } else {
            if (!range.contains(value)) {
                addError(new FieldMessage(field, "Must be on collection: " + range));
                return false;
            }
        }

        return true;
    }

    public boolean length(String field, String value, int min, int max, boolean required) {
        if (value == null) {
            if (required)
                addError(new FieldMessage(field, "Must not be null"));

            return false;
        } else {
            if (value.length() < min && value.length() > max) {
                addError(new FieldMessage(field, "Must be between " + min + " and " + max + " characters"));
                return false;
            }
        }

        return true;
    }

    public boolean max(String field, Number value, Number max, boolean required) {
        if (value == null) {
            if (required)
                addError(new FieldMessage(field, "Must not be null"));

            return false;
        } else {
            if (!value.getClass().equals(max.getClass())) {
                addError(new FieldMessage(field, "Method parameter (max) must be of same class of method parameter (value)"));
                return false;
            } else {
                if (value.getClass().isAssignableFrom(Integer.class)) {
                    if (Integer.parseInt(value.toString()) >= Integer.parseInt(max.toString())) {
                        addError(new FieldMessage(field, "Must be less than or equal " + max));
                        return false;
                    }
                }

                if (value.getClass().isAssignableFrom(Long.class)) {
                    if (Long.parseLong(value.toString()) >= Long.parseLong(max.toString())) {
                        addError(new FieldMessage(field, "Must be less than or equal " + max));
                        return false;
                    }
                }

                if (value.getClass().isAssignableFrom(Double.class)) {
                    if (Double.parseDouble(value.toString()) >= Double.parseDouble(max.toString())) {
                        addError(new FieldMessage(field, "Must be less than or equal " + max));
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public boolean min(String field, Number value, Number min, boolean required) {
        if (value == null) {
            if (required)
                addError(new FieldMessage(field, "Must not be null"));

            return false;
        } else {
            if (value.getClass().equals(min.getClass())) {
                addError(new FieldMessage(field, "Method parameter (min) must be of same class of method parameter (value)"));
                return false;
            } else {
                if (value.getClass().isAssignableFrom(Integer.class)) {
                    if (Integer.parseInt(value.toString()) <= Integer.parseInt(min.toString())) {
                        addError(new FieldMessage(field, "Must be greater than or equal " + min));
                        return false;
                    }
                }

                if (value.getClass().isAssignableFrom(Long.class)) {
                    if (Long.parseLong(value.toString()) <= Long.parseLong(min.toString())) {
                        addError(new FieldMessage(field, "Must be greater than or equal " + min));
                        return false;
                    }
                }

                if (value.getClass().isAssignableFrom(Double.class)) {
                    if (Double.parseDouble(value.toString()) <= Double.parseDouble(min.toString())) {
                        addError(new FieldMessage(field, "Must be greater than or equal " + min));
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public boolean notEmpty(String field, Object value, boolean required) {
        if (value == null) {
            if (required)
                addError(new FieldMessage(field, "Must not be null"));

            return false;
        } else {
            if (Collection.class.isAssignableFrom(value.getClass())) {
                if (((Collection<?>) value).isEmpty()) {
                    if (required)
                        addError(new FieldMessage(field, "Must not be empty"));

                    return false;
                }
            } else {
                if (value.toString().isBlank()) {
                    addError(new FieldMessage(field, "Must not be empty"));
                    return false;
                }
            }

        }

        return true;
    }

    public boolean notIn(String field, Integer value, List<Integer> range, boolean required) {
        if (value == null) {
            if (required)
                addError(new FieldMessage(field, "Must not be null"));

            return false;
        } else {
            if (range.contains(value)) {
                addError(new FieldMessage(field, "Must not be on collection: " + range));
                return false;
            }
        }

        return true;
    }

    public boolean notNull(String field, Object value, boolean required) {
        if (value == null) {
            if (required)
                addError(new FieldMessage(field, "Must not be null"));

            return false;
        }

        return true;
    }

    public boolean pattern(String field, String value, String pattern, boolean required) {
        if (value == null) {
            if (required)
                addError(new FieldMessage(field, "Must not be null"));

            return false;
        } else {
            if (!Pattern.compile(pattern).matcher(value).matches()) {
                addError(new FieldMessage(field, "Must match RegEx pattern: " + pattern));
                return false;
            }
        }

        return true;
    }

    public boolean positive(String field, Number value, boolean required) {
        if (value == null) {
            if (required)
                addError(new FieldMessage(field, "Must not be null"));

            return false;
        } else {
            if (value.getClass().isAssignableFrom(Integer.class)) {
                if (((Integer) value) <= 0) {
                    addError(new FieldMessage(field, "Must be positive"));
                    return false;
                }
            }

            if (value.getClass().isAssignableFrom(Long.class)) {
                if (((Long) value) <= 0) {
                    addError(new FieldMessage(field, "Must be positive"));
                    return false;
                }
            }

            if (value.getClass().isAssignableFrom(Double.class)) {
                if (((Double) value) <= 0) {
                    addError(new FieldMessage(field, "Must be positive"));
                    return false;
                }
            }
        }

        return true;
    }

    public boolean positiveOrZero(String field, Object value, boolean required) {
        if (value == null) {
            if (required)
                addError(new FieldMessage(field, "Must not be null"));

            return false;
        } else {
            if (value.getClass().isAssignableFrom(Integer.class)) {
                if (((Integer) value) < 0) {
                    addError(new FieldMessage(field, "Must be positive or zero"));
                    return false;
                }
            }

            if (value.getClass().isAssignableFrom(Long.class)) {
                if (((Long) value) < 0) {
                    addError(new FieldMessage(field, "Must be positive or zero"));
                    return false;
                }
            }

            if (value.getClass().isAssignableFrom(Double.class)) {
                if (((Double) value) < 0) {
                    addError(new FieldMessage(field, "Must be positive or zero"));
                    return false;
                }
            }
        }

        return true;
    }

    public boolean range(String field, Object value, Object min, Object max, boolean required) {
        if (value == null) {
            if (required)
                addError(new FieldMessage(field, "Must not be null"));

            return false;
        } else {
            if (!value.getClass().equals(min.getClass()) || !value.getClass().equals(max.getClass())) {
                addError(new FieldMessage(field, "Method parameters (min, max) must be of same class of method parameter (value)"));
                return false;
            } else {
                if (value.getClass().isAssignableFrom(Date.class)) {
                    if (((Date) value).before(((Date) min))  || ((Date) value).after(((Date) max))) {
                        addError(new FieldMessage(field, "Must be between " + min + " and " + max));
                        return false;
                    }
                } else if (value.getClass().isAssignableFrom(Integer.class)) {
                    if (((Integer) value) < ((Integer) min) || ((Integer) value) > ((Integer) max)) {
                        addError(new FieldMessage(field, "Must be between " + min + " and " + max));
                        return false;
                    }
                } else if (value.getClass().isAssignableFrom(Long.class)) {
                    if (((Long) value) < ((Long) min) || ((Long) value) > ((Long) max)) {
                        addError(new FieldMessage(field, "Must be between " + min + " and " + max));
                        return false;
                    }
                } else if (value.getClass().isAssignableFrom(Double.class)) {
                    if (((Double) value) < ((Double) min) || ((Double) value) > ((Double) max)) {
                        addError(new FieldMessage(field, "Must be between " + min + " and " + max));
                        return false;
                    }
                } else {
                    addError(new FieldMessage(field, "Cannot validate ranges for " + value.getClass() + " values"));
                    return false;
                }
            }
        }

        return true;
    }

    public boolean size(String field, Object value, int size, boolean required) {
        if (value == null) {
            if (required)
                addError(new FieldMessage(field, "Must not be null"));

            return false;
        } else {
            if (Collection.class.isAssignableFrom(value.getClass())) {
                if (((Collection<?>) value).size() != size) {
                    addError(new FieldMessage(field, "Must have " + size + " element(s)"));
                    return false;
                }
            } else {
                if (value.toString().length() != size) {
                    addError(new FieldMessage(field, "Must have " + size + " character(s)"));
                    return false;
                }
            }
        }

        return true;
    }

    public static List<FieldMessage> handleErrors(List<FieldMessage> errors, String parentField) {
        List<FieldMessage> aux = new ArrayList<>();

        errors.forEach(msg -> {
            msg.setField(parentField + "." + msg.getField());
            aux.add(msg);
        });

        return aux;
    }
}
