package org.firas.common.controller;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.springframework.web.client.RestClientException;
import org.springframework.data.domain.Page;

import org.firas.common.datatype.ModelParser;
import org.firas.common.response.JsonResponseFailUndefined;
import org.firas.common.response.JsonResponseNetworkError;

public abstract class ResponseController {

    private <T> ArrayList<HashMap<String, Object>> getListFromPage(
            Page<T> page, ModelParser<T> dataParser) {
        ArrayList<HashMap<String, Object>> list =
                new ArrayList<HashMap<String, Object>>();
        Iterator<T> iterator = page.iterator();
        while (iterator.hasNext()) {
            list.add( dataParser.parse(iterator.next()) );
        }
        return list;
    }

    protected <T> HashMap<String, Object> parsePage(
            Page<T> page, ModelParser<T> dataParser) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("page", Math.min(page.getNumber() + 1, page.getTotalPages()));
        result.put("pages", page.getTotalPages());
        result.put("is_first", page.isFirst());
        result.put("is_last", page.isLast());
        result.put("total", page.getTotalElements());
        result.put("list", getListFromPage(page, dataParser));
        return result;
    }

    protected JsonResponseFailUndefined dealWithInternalException(
            Exception ex, String message) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(buffer, true);
        ex.printStackTrace();
        ex.printStackTrace(stream);
        String content = buffer.toString();
        try {
            stream.close();
        } catch (Exception e) {
            ex.printStackTrace();
        }
        return new JsonResponseFailUndefined(message,
                "Internal Server Exception: [" + ex.getClass().getName() +
                "] " + ex.getMessage(), content);
    }

    protected JsonResponseNetworkError dealWithRestClientException(
            RestClientException ex) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(buffer, true);
        ex.printStackTrace();
        ex.printStackTrace(stream);
        String content = buffer.toString();
        try {
            stream.close();
        } catch (Exception e) {
            ex.printStackTrace();
        }
        return new JsonResponseNetworkError("网络似乎出现了问题",
                ex.getClass().getName() + ": " + ex.getMessage(),
                content);
    }
}
