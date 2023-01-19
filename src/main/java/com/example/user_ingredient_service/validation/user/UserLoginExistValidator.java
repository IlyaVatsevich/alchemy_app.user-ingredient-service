package com.example.user_ingredient_service.validation.user;

import com.example.user_ingredient_service.annotation.user.ValidExistUserLogin;
import com.example.user_ingredient_service.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserLoginExistValidator implements ConstraintValidator<ValidExistUserLogin,String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (null == value || value.isBlank()) {
            return true;
        }

        return !userRepository.existsByLogin(value);
    }
}
