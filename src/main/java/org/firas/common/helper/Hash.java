package org.firas.common.helper;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {

    public static byte[] md5(String str) {
        return md5(str, "UTF-8");
    }

    public static byte[] md5(String str, String charset) {
        try {
            return md5(str.getBytes(charset));
        } catch (UnsupportedEncodingException ex) {
        }
        return null;
    }

    public static byte[] md5(String str, Charset charset) {
        return md5(str.getBytes(charset));
    }

    public static byte[] md5(byte[] bytes) {
        byte[] codes = null;
        try {
            MessageDigest digestor = MessageDigest.getInstance("MD5");
            digestor.update(bytes);
            codes = digestor.digest();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return codes;
    }


    public static byte[] sha1(String str) {
        return sha1(str, "UTF-8");
    }

    public static byte[] sha1(String str, String charset) {
        try {
            return sha1(str.getBytes(charset));
        } catch (UnsupportedEncodingException ex) {
        }
        return null;
    }

    public static byte[] sha1(String str, Charset charset) {
        return sha1(str.getBytes(charset));
    }

    public static byte[] sha1(byte[] bytes) {
        byte[] codes = null;
        try {
            MessageDigest digestor = MessageDigest.getInstance("SHA-1");
            digestor.update(bytes);
            codes = digestor.digest();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return codes;
    }


    public static byte[] sha256(String str) {
        return sha256(str, "UTF-8");
    }

    public static byte[] sha256(String str, String charset) {
        try {
            return sha256(str.getBytes(charset));
        } catch (UnsupportedEncodingException ex) {
        }
        return null;
    }

    public static byte[] sha256(String str, Charset charset) {
        return sha256(str.getBytes(charset));
    }

    public static byte[] sha256(byte[] bytes) {
        byte[] codes = null;
        try {
            MessageDigest digestor = MessageDigest.getInstance("SHA-256");
            digestor.update(bytes);
            codes = digestor.digest();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return codes;
    }
}
