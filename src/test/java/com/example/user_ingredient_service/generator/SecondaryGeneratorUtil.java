package com.example.user_ingredient_service.generator;

import java.util.Random;

public class SecondaryGeneratorUtil {

    private SecondaryGeneratorUtil(){}
    private static final Random RND = new Random();

    public static String generateRndStr() {

        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 10;

        return RND.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

    }

    public static Integer generatePositiveInteger() {
        return RND.ints(0, Integer.MAX_VALUE)
                .findAny()
                .getAsInt();
    }

    public static Integer generateNegativeInteger() {
        return RND.ints(Integer.MIN_VALUE, -1)
                .findAny()
                .getAsInt();
    }

    public static Short generateShortForValidLossProbability() {
        return ((short) RND.ints(0, 100)
                .findAny()
                .getAsInt());
    }

    public static Short generateShortForInvalidLossProbability() {
        return ((short) RND.ints(Short.MIN_VALUE, Short.MAX_VALUE)
                .filter(value -> value>100||value<0)
                .findAny()
                .getAsInt());
    }
}
