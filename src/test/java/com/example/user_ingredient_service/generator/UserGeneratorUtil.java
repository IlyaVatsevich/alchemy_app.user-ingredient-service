package com.example.user_ingredient_service.generator;

import com.example.user_ingredient_service.entity.User;

import static com.example.user_ingredient_service.generator.SecondaryGeneratorUtil.*;

public class UserGeneratorUtil {

    private UserGeneratorUtil(){}

    public static User createValidUser() {
        return User.builder()
                .withPassword("P@ssword1!")
                .withMail(generateRndStr() + generatePositiveInteger() + "@yopmail.com")
                .withLogin(generateRndStr() + generatePositiveInteger())
                .withGold(generatePositiveInteger())
                .build();
    }
}
