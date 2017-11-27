package org.firas.wrap.helper;

public final class IntegerPaddingHelper {

    private IntegerPaddingHelper() {}

    public static String padTo(final int number, final int length) {
        if (length < 1) {
            throw new IllegalArgumentException("The length must be a positive integer");
        }
        final StringBuilder result = new StringBuilder();
        final StringBuilder temp = new StringBuilder();
        if (number >= 0) {
            temp.append('0');
        } else {
            result.append('-');
        }
        temp.append(number);
        for (int times = length + 1 - temp.length(); times > 0; times -= 1) {
            result.append('0');
        }
        result.append(temp.substring(1));
        return result.toString();
    }
}
