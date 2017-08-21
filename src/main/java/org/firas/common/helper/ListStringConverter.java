package org.firas.common.helper;

import java.util.Iterator;

public class ListStringConverter {

    public static String iterableToString(Iterable iterable, char seperator) {
        StringBuilder buffer = new StringBuilder();
        Iterator iterator = iterable.iterator();
        while (iterator.hasNext()) {
            buffer.append(seperator);
            buffer.append(String.valueOf(iterator.next()));
        }
        return buffer.substring(1);
    }

    public static String iterableToString(Iterable iterable, String seperator) {
        StringBuilder buffer = new StringBuilder();
        Iterator iterator = iterable.iterator();
        while (iterator.hasNext()) {
            buffer.append(seperator);
            buffer.append(String.valueOf(iterator.next()));
        }
        return buffer.substring(seperator.length());
    }
}
