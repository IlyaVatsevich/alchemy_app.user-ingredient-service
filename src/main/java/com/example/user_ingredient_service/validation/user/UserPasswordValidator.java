package com.example.user_ingredient_service.validation.user;

import com.example.user_ingredient_service.annotation.user.ValidUserPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserPasswordValidator implements ConstraintValidator<ValidUserPassword,String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        final String passPattern = "[\\p{L}0-9~\"():;<>@\\[\\]!#$%&'*+-/=?^_`{|}]+";

        if (null == value || value.isBlank()) {
            return true;
        }

        return upperCaseEnglishAlphaCharIsPresent(value)
                && lowerCaseEnglishAlphaCharIsPresent(value)
                && digitIsPresent(value)
                && value.matches(passPattern);
    }

    private boolean upperCaseEnglishAlphaCharIsPresent(String password) {
        return password.codePoints()
                .filter(Character::isUpperCase)
                .mapToObj(Character.UnicodeBlock::of)
                .anyMatch(c->c.equals(Character.UnicodeBlock.BASIC_LATIN));
    }

    private boolean lowerCaseEnglishAlphaCharIsPresent(String password) {
        return password.codePoints()
                .filter(Character::isLowerCase)
                .mapToObj(Character.UnicodeBlock::of)
                .anyMatch(c->c.equals(Character.UnicodeBlock.BASIC_LATIN));
    }

    private boolean digitIsPresent(String password) {
        return password.codePoints().anyMatch(Character::isDigit);
    }

}
