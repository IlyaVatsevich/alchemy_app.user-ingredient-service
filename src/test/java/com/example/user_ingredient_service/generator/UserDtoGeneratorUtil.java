package com.example.user_ingredient_service.generator;

import com.example.user_ingredient_service.dto.UserLogDto;
import com.example.user_ingredient_service.dto.UserRegDto;

import static com.example.user_ingredient_service.generator.SecondaryGeneratorUtil.*;

public class UserDtoGeneratorUtil {

    private UserDtoGeneratorUtil() {}

    public static UserRegDto createValidUserForReg() {
        return UserRegDto.builder()
                .withLogin(generateRndStr())
                .withPassword("P@ssword1!")
                .withMail(generateRndStr() +
                        generatePositiveInteger() +
                        "@yopmail.com")
                .build();
    }

    public static UserRegDto createUserWithInvalidMail() {
        return UserRegDto.builder()
                .withLogin(generateRndStr())
                .withPassword("P@ssword1!")
                .withMail(generateRndStr())
                .build();
    }

    public static UserRegDto createUserWithInvalidPassword() {
        return UserRegDto.builder()
                .withLogin(generateRndStr())
                .withPassword("P@ssword!")
                .withMail(generateRndStr())
                .build();
    }

    public static UserLogDto createValidLogUser() {
        return UserLogDto
                .builder()
                .withLogin("user_1")
                .withPassword("P@ssword1")
                .build();
    }

    public static UserLogDto createNotExistingLogUser() {
        return UserLogDto
                .builder()
                .withLogin(generateRndStr())
                .withPassword("P@ssword1")
                .build();
    }

    public static UserLogDto createExistingUserWithInvalidPassword() {
        return UserLogDto
                .builder()
                .withLogin("user_1")
                .withPassword("P@ssword1!")
                .build();
    }

}
