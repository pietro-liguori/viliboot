package com.vili.demo.validation.update.constraint;

import com.vili.demo.validation.update.UpdateTutorValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UpdateTutorValidator.class)
@Target({ ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUpdateTutor {
    
    String message() default "Tutor update validation error";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
