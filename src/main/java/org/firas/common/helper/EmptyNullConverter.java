package org.firas.common.helper;

import java.math.BigDecimal;
import java.math.BigInteger;

public class EmptyNullConverter {

    public static Integer nullIfZero(int value) {
        return 0 == value ? null : new Integer(value);
    }

    public static Long nullIfZero(long value) {
        return 0 == value ? null : new Long(value);
    }

    public static Float nullIfZero(float value) {
        return 0 == value ? null : new Float(value);
    }

    public static Double nullIfZero(double value) {
        return 0 == value ? null : new Double(value);
    }

    public static Byte nullIfZero(byte value) {
        return 0 == value ? null : new Byte(value);
    }

    public static Short nullIfZero(short value) {
        return 0 == value ? null : new Short(value);
    }

    public static BigInteger nullIfZero(BigInteger value) {
        return BigInteger.ZERO.compareTo(value) == 0 ? null : value;
    }

    public static BigDecimal nullIfZero(BigDecimal value) {
        return BigDecimal.ZERO.compareTo(value) == 0 ? null : value;
    }


    public static int zeroIfNull(Integer value) {
        return null == value ? 0 : value.intValue();
    }

    public static long zeroIfNull(Long value) {
        return null == value ? 0 : value.longValue();
    }

    public static float zeroIfNull(Float value) {
        return null == value ? 0 : value.floatValue();
    }

    public static double zeroIfNull(Double value) {
        return null == value ? 0 : value.doubleValue();
    }

    public static byte zeroIfNull(Byte value) {
        return null == value ? 0 : value.byteValue();
    }

    public static short zeroIfNull(Short value) {
        return null == value ? 0 : value.shortValue();
    }

    public static BigInteger zeroBigIntegerIfNull(String integer) {
        return null == integer ? BigInteger.ZERO : new BigInteger(integer);
    }

    public static BigDecimal zeroBigDecimalIfNull(String number) {
        return null == number ? BigDecimal.ZERO : new BigDecimal(number);
    }

    public static <T extends CharSequence> T nullIfEmpty(T value) {
        return null == value || value.length() <= 0 ? null : value;
    }

    public static String emptyIfNull(String value) {
        return null == value ? "" : value;
    }
    public static StringBuilder emptyIfNull(StringBuilder value) {
        return null == value ? new StringBuilder() : value;
    }
    public static StringBuffer emptyIfNull(StringBuffer value) {
        return null == value ? new StringBuffer() : value;
    }
}
