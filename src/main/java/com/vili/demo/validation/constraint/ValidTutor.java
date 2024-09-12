package com.vili.demo.validation.constraint;

import com.vili.demo.validation.TutorValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TutorValidator.class)
@Target({ ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTutor {

    String message() default "Tutor validation error";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
