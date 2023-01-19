package com.example.user_ingredient_service.annotation.user;


import com.example.user_ingredient_service.validation.user.UserLoginExistValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserLoginExistValidator.class)
public @interface ValidExistUserLogin {

    String message() default "User with such login already exist.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
