package com.example.user_ingredient_service.validation.user;

import com.example.user_ingredient_service.annotation.user.ValidUserLogin;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserLoginValidator implements ConstraintValidator<ValidUserLogin,String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (null== value || value.isBlank()) {
            return true;
        }

        final String loginPatten = "[\\p{L}0-9~\"():;<>@\\[\\]!#$%&'*+-/=?^_`{|}]+";

        return value.matches(loginPatten);
    }
}
