package org.firas.common.helper;

import java.math.BigDecimal;
import java.math.BigInteger;

public class NumberConverter {

    public static BigDecimal toBigDecimal(Object obj) {
        if (obj instanceof BigDecimal) return BigDecimal.class.cast(obj);
        if (obj instanceof BigInteger) {
            return new BigDecimal(BigInteger.class.cast(obj));
        }
        if (obj instanceof Integer) {
            return new BigDecimal(Integer.class.cast(obj).intValue());
        }
        if (obj instanceof Long) {
            return new BigDecimal(Long.class.cast(obj).longValue());
        }
        if (obj instanceof Number) {
            return new BigDecimal(Number.class.cast(obj).doubleValue());
        }
        if (obj instanceof String) {
            return new BigDecimal(String.class.cast(obj));
        }
        throw new IllegalArgumentException("Objects of type [" +
                obj.getClass().getName() + "] cannot be converted to a " +
                "BigDecimal");
    }

    public static BigInteger toBigInteger(Object obj) {
        if (obj instanceof BigInteger) return BigInteger.class.cast(obj);
        if (obj instanceof BigDecimal) {
            return BigDecimal.class.cast(obj).toBigInteger();
        }
        if (obj instanceof Integer) {
            int n = Integer.class.cast(obj).intValue();
            byte[] bytes = new byte[4];
            bytes[3] = (byte)(0xFF & n);
            bytes[2] = (byte)(0xFF & (n >>= 8));
            bytes[1] = (byte)(0xFF & (n >>= 8));
            bytes[0] = (byte)(0xFF & (n >> 8));
            return new BigInteger(bytes);
        }
        if (obj instanceof Number) {
            long n = Number.class.cast(obj).longValue();
            byte[] bytes = new byte[8];
            bytes[7] = (byte)(0xFF & n);
            for (int i = 6; i >= 1; --i) {
                bytes[i] = (byte)(0xFF & (n >>= 8));
            }
            bytes[0] = (byte)(0xFF & (n >> 8));
            return new BigInteger(bytes);
        }
        if (obj instanceof String) {
            return new BigInteger(String.class.cast(obj));
        }
        throw new IllegalArgumentException("Objects of type [" +
                obj.getClass().getName() + "] cannot be converted to a " +
                "BigInteger");
    }
}
