package com.example.user_ingredient_service.annotation.user;

import com.example.user_ingredient_service.validation.user.UserPasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserPasswordValidator.class)
public @interface ValidUserPassword {

    String message() default "Invalid password.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
