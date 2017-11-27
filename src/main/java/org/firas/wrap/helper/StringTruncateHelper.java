package org.firas.wrap.helper;

public final class StringTruncateHelper {

    private StringTruncateHelper() {}

    public static String truncateTo(final String str, final int length) {
        if (null == str) {
            throw new IllegalArgumentException("\"str\" cannot be null");
        }
        if (length < 4) {
            throw new IllegalArgumentException("The length must be at least 4");
        }
        if (str.length() < length - 3) {
            return str;
        }
        StringBuilder result = new StringBuilder(str.substring(0, length - 3));
        result.append("...");
        return result.toString();
    }
}
