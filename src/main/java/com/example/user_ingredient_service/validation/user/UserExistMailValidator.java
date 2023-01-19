package com.example.user_ingredient_service.validation.user;

import com.example.user_ingredient_service.annotation.user.ValidExistUserMail;
import com.example.user_ingredient_service.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserExistMailValidator implements ConstraintValidator<ValidExistUserMail,String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (null == value || value.isBlank()) {
            return true;
        }

        return !userRepository.existsByMail(value);
    }
}
