package org.firas.common.helper;

public class CamelUnderscoreHelper {

    public static String camel2Underscore(String str) {
        int length = str.length();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; ) {
            char c = str.charAt(i++);
            if (Character.isUpperCase(c)) {
                result.append('_');
                result.append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static String underscore2Camel(String str) {
        int length = str.length();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; ) {
            char c = str.charAt(i++);
            if ('_' == c) {
                do {
                    c = str.charAt(i++);
                    if ('_' != c) {
                        result.append(Character.toUpperCase(c));
                        break;
                    }
                } while (i < length);
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
