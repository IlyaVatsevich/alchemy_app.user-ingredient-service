package com.example.user_ingredient_service.generator;

import com.example.user_ingredient_service.dto.UserRegDto;

public class UserDtoGeneratorUtil {

    private UserDtoGeneratorUtil() {}

    public static UserRegDto createValidUserForReg() {
        return UserRegDto.builder().
                withLogin(SecondaryGeneratorUtil.generateRndStr()).
                withPassword("P@ssword1!").
                withMail(SecondaryGeneratorUtil.generateRndStr() +
                        SecondaryGeneratorUtil.generatePositiveInteger() +
                        "@yopmail.com").
                build();
    }

}
