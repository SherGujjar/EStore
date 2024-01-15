package com.example.ElectronicStore.Validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EmailValidation.class)
public @interface EmailValidator{
    String message() default "{Invalid email}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
