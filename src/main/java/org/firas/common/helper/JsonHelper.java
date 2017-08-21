package org.firas.common.helper;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonMappingException;

public class JsonHelper {

    private StringBuilder buffer;

    public JsonHelper() {
        buffer = new StringBuilder();
    }

    public String toString() {
        return buffer.toString();
    }

    public JsonHelper objectBegin() {
        buffer.append('{');
        return this;
    }

    public JsonHelper objectEnd() {
        buffer.append('}');
        return this;
    }

    public JsonHelper arrayBegin() {
        buffer.append('[');
        return this;
    }

    public JsonHelper arrayEnd() {
        buffer.append(']');
        return this;
    }

    public JsonHelper seperator() {
        buffer.append(',');
        return this;
    }

    public JsonHelper append(String key, int integer) {
        buffer.append('"');
        buffer.append(key);
        buffer.append("\":");
        buffer.append(integer);
        return this;
    }

    public JsonHelper append(String key, long integer) {
        buffer.append('"');
        buffer.append(key);
        buffer.append("\":");
        buffer.append(integer);
        return this;
    }

    public JsonHelper append(String key, float number) {
        buffer.append('"');
        buffer.append(key);
        buffer.append("\":");
        buffer.append(number);
        return this;
    }

    public JsonHelper append(String key, double number) {
        buffer.append('"');
        buffer.append(key);
        buffer.append("\":");
        buffer.append(number);
        return this;
    }

    public JsonHelper append(String key, boolean value) {
        buffer.append('"');
        buffer.append(key);
        buffer.append("\":");
        buffer.append(value);
        return this;
    }

    public JsonHelper append(String key, Number number) {
        buffer.append('"');
        buffer.append(key);
        buffer.append("\":");
        buffer.append(number);
        return this;
    }

    public JsonHelper append(String key, Boolean value) {
        buffer.append('"');
        buffer.append(key);
        buffer.append("\":");
        buffer.append(value);
        return this;
    }

    public JsonHelper append(String key, String value) {
        buffer.append('"');
        buffer.append(key);
        buffer.append("\":\"");
        buffer.append(value.replace("\\", "\\\\").replace("\"", "\\\"").
                replace("\t", "\\t").replace("\n", "\\n"));
        buffer.append("\"");
        return this;
    }

    public JsonHelper append(String key, JsonHelper value) {
        buffer.append('"');
        buffer.append(key);
        buffer.append("\":");
        buffer.append(value.toString());
        return this;
    }


    public static String toJson(String key, int integer) {
        StringBuilder result = new StringBuilder("\"");
        result.append(key);
        result.append("\":");
        result.append(integer);
        return result.toString();
    }

    public static String toJson(String key, long integer) {
        StringBuilder result = new StringBuilder("\"");
        result.append(key);
        result.append("\":");
        result.append(integer);
        return result.toString();
    }

    public static String toJson(String key, float number) {
        StringBuilder result = new StringBuilder("\"");
        result.append(key);
        result.append("\":");
        result.append(number);
        return result.toString();
    }

    public static String toJson(String key, double number) {
        StringBuilder result = new StringBuilder("\"");
        result.append(key);
        result.append("\":");
        result.append(number);
        return result.toString();
    }

    public static String toJson(String key, boolean value) {
        StringBuilder result = new StringBuilder("\"");
        result.append(key);
        result.append("\":");
        result.append(value);
        return result.toString();
    }

    public static String toJson(String key, Number number) {
        StringBuilder result = new StringBuilder("\"");
        result.append(key);
        result.append("\":");
        result.append(number);
        return result.toString();
    }

    public static String toJson(String key, Boolean value) {
        StringBuilder result = new StringBuilder("\"");
        result.append(key);
        result.append("\":");
        result.append(value);
        return result.toString();
    }

    public static String toJson(String key, String value) {
        StringBuilder result = new StringBuilder("\"");
        result.append(key);
        result.append("\":\"");
        result.append(value.replace("\\", "\\\\").replace("\"", "\\\"").
                replace("\t", "\\t").replace("\n", "\\n"));
        result.append("\"");
        return result.toString();
    }

    public static Map<String, Object> toStringObjectMap(String json)
            throws IOException {
        TypeReference<Map<String, Object>> mapType =
                new TypeReference<Map<String, Object>>(){};
        return new ObjectMapper().readValue(json, mapType);
    }
}
