package org.firas.common.helper;

import java.util.Random;

public class RandomHelper {

    private static Random random = new Random();

    public static String randomNumericString(int length) {
        return randomNumericString(length, random);
    }

    public static String randomNumericString(int length, Random random) {
        if (length <= 0) throw new IllegalArgumentException(
                "The length cannot be non-positive");
        StringBuilder buffer = new StringBuilder();
        while (length-- > 0) {
            int temp = '0' + random.nextInt(10);
            buffer.append((char)temp);
        }
        return buffer.toString();
    }

    public static String randomUpperCaseString(int length) {
        return randomUpperCaseString(length, random);
    }

    public static String randomUpperCaseString(int length, Random random) {
        if (length <= 0) throw new IllegalArgumentException(
                "The length cannot be non-positive");
        StringBuilder buffer = new StringBuilder();
        while (length-- > 0) {
            int temp = 'A' + random.nextInt(26);
            buffer.append((char)temp);
        }
        return buffer.toString();
    }

    public static String randomLowerCaseString(int length) {
        return randomLowerCaseString(length, random);
    }

    public static String randomLowerCaseString(int length, Random random) {
        if (length <= 0) throw new IllegalArgumentException(
                "The length cannot be non-positive");
        StringBuilder buffer = new StringBuilder();
        while (length-- > 0) {
            int temp = 'a' + random.nextInt(26);
            buffer.append((char)temp);
        }
        return buffer.toString();
    }

    public static String randomAlphabetString(int length) {
        return randomAlphabetString(length, random);
    }

    public static String randomAlphabetString(int length, Random random) {
        if (length <= 0) throw new IllegalArgumentException(
                "The length cannot be non-positive");
        StringBuilder buffer = new StringBuilder();
        while (length-- > 0) {
            int temp = random.nextInt(26 << 1);
            if (temp < 26) buffer.append((char)('A' + temp));
            else buffer.append((char)('a' + temp - 26));
        }
        return buffer.toString();
    }

    public static String randomAlphaNumericString(int length) {
        return randomAlphaNumericString(length, random);
    }

    public static String randomAlphaNumericString(int length, Random random) {
        if (length <= 0) throw new IllegalArgumentException(
                "The length cannot be non-positive");
        StringBuilder buffer = new StringBuilder();
        while (length-- > 0) {
            int temp = random.nextInt(10 + (26 << 1));
            if (temp < 26) buffer.append((char)('A' + temp));
            else if ((temp -= 26) < 26 ) buffer.append((char)('a' + temp));
            else buffer.append((char)('0' + temp - 26));
        }
        return buffer.toString();
    }
}
