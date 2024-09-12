package com.vili.demo.validation.insert.constraint;

import com.vili.demo.validation.insert.InsertTutorValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = InsertTutorValidator.class)
@Target({ ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidInsertTutor {

    String message() default "Tutor insert validation error";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
