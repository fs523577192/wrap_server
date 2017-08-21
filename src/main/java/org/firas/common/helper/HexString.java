package org.firas.common.helper;

import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.nio.charset.Charset;
import java.io.UnsupportedEncodingException;

public class HexString {

    public static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
                '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String byteArray2HexString(byte[] bytes) {
        return byteArray2HexString(bytes, "");
    }

    public static String byteArray2HexString(byte[] bytes, char seperator) {
        if (bytes.length <= 0) return "";
        StringBuilder buffer = new StringBuilder(
                (bytes.length << 1) + bytes.length - 1);
        buffer.append(HEX_DIGITS[((int)bytes[0] & 0xF0) >> 4]);
        buffer.append(HEX_DIGITS[(int)bytes[0] & 0xF]);
        for (int i = 1; i < bytes.length; ++i) {
            buffer.append(seperator);
            buffer.append(HEX_DIGITS[((int)bytes[i] & 0xF0) >> 4]);
            buffer.append(HEX_DIGITS[(int)bytes[i] & 0xF]);
        }
        return buffer.toString();
    }

    public static String byteArray2HexString(byte[] bytes, String seperator) {
        if (bytes.length <= 0) return "";
        StringBuilder buffer = new StringBuilder(
                (bytes.length << 1) + (bytes.length - 1) * seperator.length());
        buffer.append(HEX_DIGITS[((int)bytes[0] & 0xF0) >> 4]);
        buffer.append(HEX_DIGITS[(int)bytes[0] & 0xF]);
        for (int i = 1; i < bytes.length; ++i) {
            buffer.append(seperator);
            buffer.append(HEX_DIGITS[((int)bytes[i] & 0xF0) >> 4]);
            buffer.append(HEX_DIGITS[(int)bytes[i] & 0xF]);
        }
        return buffer.toString();
    }


    public static String bytes2HexString(Iterable<Byte> bytes) {
        return bytes2HexString(bytes, "");
    }

    public static String bytes2HexString(Iterable<Byte> bytes, char seperator) {
        Iterator<Byte> iterator = bytes.iterator();
        if (!iterator.hasNext()) return "";
        StringBuilder buffer = new StringBuilder();
        int temp = (int)iterator.next();
        buffer.append(HEX_DIGITS[(temp & 0xF0) >> 4]);
        buffer.append(HEX_DIGITS[temp & 0xF]);
        while (iterator.hasNext()) {
            int i = iterator.next().intValue();
            buffer.append(seperator);
            buffer.append(HEX_DIGITS[(i & 0xF0) >> 4]);
            buffer.append(HEX_DIGITS[i & 0xF]);
        }
        return buffer.toString();
    }

    public static String bytes2HexString(Iterable<Byte> bytes, String seperator) {
        Iterator<Byte> iterator = bytes.iterator();
        if (!iterator.hasNext()) return "";
        StringBuilder buffer = new StringBuilder();
        int temp = (int)iterator.next();
        buffer.append(HEX_DIGITS[(temp & 0xF0) >> 4]);
        buffer.append(HEX_DIGITS[temp & 0xF]);
        while (iterator.hasNext()) {
            int i = iterator.next().intValue();
            buffer.append(seperator);
            buffer.append(HEX_DIGITS[(i & 0xF0) >> 4]);
            buffer.append(HEX_DIGITS[i & 0xF]);
        }
        return buffer.toString();
    }


    public static String string2HexString(String str) {
        return string2HexString(str, "UTF-8");
    }

    public static String string2HexString(String str, String charset) {
        return string2HexString(str, charset, "");
    }

    public static String string2HexString(String str, String charset, String seperator) {
        try {
            return byteArray2HexString(str.getBytes(charset), seperator);
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String string2HexString(String str, Charset charset) {
        return string2HexString(str, charset, "");
    }

    public static String string2HexString(String str, Charset charset, String seperator) {
        return byteArray2HexString(str.getBytes(charset), seperator);
    }

    public static String string2HexString(String str, char seperator) {
        return string2HexString(str, "UTF-8", seperator);
    }

    public static String string2HexString(String str, String charset, char seperator) {
        try {
            return byteArray2HexString(str.getBytes(charset), seperator);
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String string2HexString(String str, Charset charset, char seperator) {
        return byteArray2HexString(str.getBytes(charset), seperator);
    }


    public static byte[] hexString2ByteArray(String str) {
        return hexString2ByteArray(str, "");
    }

    private static byte hexChar2Byte(int c) {
        // '0' = 0x30 = 48
        // 'A' = 0x41 = 65 = 10 + 48 + 7
        // 'a' = 0x61 = 97 = 65 + 32 = 10 + 87
        if (c >= 10) c -= 7;
        if (c >= 36) c -= 32;
        if (c < 0 || c >= 16) throw new IllegalArgumentException(
                "The character is not a hexadecimal digit");
        return (byte)c;
    }

    public static byte[] hexString2ByteArray(String str, String seperator) {
        int strLength = str.length();
        if (strLength <= 0) return null;
        int sepLength = seperator.length();
        // 2 + (x - 1)(sepLength + 2) = strLength
        if (strLength < 2 || (strLength - 2) % (sepLength + 2) != 0) {
            throw new IllegalArgumentException(
                "The \"str\" parameter is not a string of hexadecimal of valid format");
        }
        byte[] result = new byte[(strLength - 2) / (sepLength + 2) + 1];
        try {
            int i = 0, j = 0;
            while (i < strLength) {
                byte temp = hexChar2Byte(str.codePointAt(i++));
                result[j++] = (byte)( (temp << 4) |
                        hexChar2Byte(str.codePointAt(i++)) );
                if (i >= strLength) break;
                if (!str.startsWith(seperator, i)) {
                    throw new IllegalArgumentException();
                }
                i += sepLength;
            }
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                "The \"str\" parameter is not a string of hexadecimal of valid format");
        }
        return result;
    }
}
