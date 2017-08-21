package org.firas.common.helper;

import java.util.Map;
import java.util.TreeSet;
import java.util.Iterator;

public class SignApiHelper {

    /**
     * API参数签名原串
     * @param  Map<String, ?> map  参数键值对
     * @param  String equalSign    放在参数名和参数值之间的符号，
     *                             默认（null的话）等于号“=”
     * @param  String andSign      放在参数之间的符号，
     *                             默认（null的话）“&”
     * @param  boolean urlEncode   是否对参数值进行urlEncode
     * @return String  按参数名升序排列的参数键值对串
     */
    public static String originalString(Map<String, ?> map,
            String equalSign, String andSign) {
        if (null == equalSign) equalSign = "=";
        if (null == andSign) andSign = "&";
        StringBuilder result = new StringBuilder();
        TreeSet<String> keySet = new TreeSet<String>(map.keySet());
        Iterator<String> iterator = keySet.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = map.get(key).toString();
            result.append(andSign);
            result.append(key);
            result.append(equalSign);
            result.append(value);
        }
        return result.substring(andSign.length());
    }

    /**
     * @param  String secretName   AppSecret参数名
     * @param  String secret       AppSecret的值
     * @param  String equalSign    放在参数名和参数值之间的符号，
     *                             默认（null的话）等于号“=”
     * @return String  AppSecret的键值对
     */
    public static String secretString(String secretName,
            String secret, String equalSign) {
        StringBuilder result = new StringBuilder();
        result.append(secretName);
        result.append(null == equalSign ? "=" : equalSign);
        result.append(secret);
        return result.toString();
    }
}
