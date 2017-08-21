package org.firas.common.service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.Serializable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.Type;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.util.MultiValueMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import lombok.extern.slf4j.Slf4j;

import org.firas.common.response.JsonResponse;
import org.firas.common.datatype.io.MyFileResource;

@Slf4j
public class HttpClientUtil {

    public enum BodyType {FORM, MULTIPART, JSON, XML};

    private static RestTemplate restTemplate;
    private static ParameterizedTypeReference<HashMap<String, Object>> mapType;
    static {
        ObjectMapper myMapper = new ObjectMapper();
        myMapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
        MappingJackson2HttpMessageConverter myConverter =
                new MappingJackson2HttpMessageConverter();
        myConverter.setObjectMapper(myMapper);

        restTemplate = new RestTemplate(
                new HttpComponentsClientHttpRequestFactory());
        List<HttpMessageConverter<?>> converters =
                restTemplate.getMessageConverters();
        for (int i = 0; i < converters.size(); ++i) {
            HttpMessageConverter converter = converters.get(i);
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                converters.set(i, myConverter);
                break;
            }
        }

        mapType = new ParameterizedTypeReference<HashMap<String, Object>>(){};
    }

    public static <T> T doRequest(
            String url,
            HttpMethod method,
            BodyType bodyType,
            Map<String, Object> bodyVariables,
            Map<String, Object> uriVariables,
            MultiValueMap<String, String> headers,
            ParameterizedTypeReference<T> responseType
    ) throws RestClientException,
            FileNotFoundException,
            IOException {
        MultiValueMap<String, Object> bodyMap =
                new LinkedMultiValueMap<String, Object>();
        if (null != bodyVariables) {
            for (Map.Entry<String, Object> entry : bodyVariables.entrySet()) {
                Object value = entry.getValue();
                if (value instanceof Number) {
                    value = ((Number)value).toString();
                } else if (value instanceof MultipartFile) {
                    value = MyFileResource.getInstance((MultipartFile)value);
                } else if (value instanceof File) {
                    value = MyFileResource.getInstance((File)value);
                }
                bodyMap.set(entry.getKey(), value);
            }
        }
        if (null == uriVariables) {
            uriVariables = new HashMap<String, Object>();
        }
        if (null == headers) {
            headers = new HttpHeaders();
        }
        if (BodyType.MULTIPART == bodyType) {
                headers.set(HttpHeaders.CONTENT_TYPE,
                        MediaType.MULTIPART_FORM_DATA_VALUE);
        } else if (BodyType.JSON == bodyType) {
                headers.set(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON_VALUE);
        } else if (BodyType.XML == bodyType) {
                headers.set(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_XML_VALUE);
        } else {
                headers.set(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        }
        if (uriVariables.size() > 0) url = url + '?';
        for (Map.Entry<String, Object> entry : uriVariables.entrySet()) {
            String key = entry.getKey();
            url = url + key + "={" + key + "}&";
        }
        HttpEntity<MultiValueMap<String, Object>> body =
                new HttpEntity<MultiValueMap<String, Object>>(
                        bodyMap, headers);
        return restTemplate.exchange(url, method, body,
                responseType, uriVariables).getBody();
    }

    public static <T> T doPost(String url,
            BodyType bodyType,
            Map<String, Object> bodyVariables,
            Map<String, Object> uriVariables,
            MultiValueMap<String, String> headers,
            ParameterizedTypeReference<T> responseType
    ) throws RestClientException,
            FileNotFoundException,
            IOException {
        return doRequest(url, HttpMethod.POST, bodyType,
                bodyVariables, uriVariables, headers, responseType);
    }

    public static <T> T doPatch(String url,
            BodyType bodyType,
            Map<String, Object> bodyVariables,
            Map<String, Object> uriVariables,
            MultiValueMap<String, String> headers,
            ParameterizedTypeReference<T> responseType
    ) throws RestClientException,
            FileNotFoundException,
            IOException {
        return doRequest(url, HttpMethod.PATCH, bodyType,
                bodyVariables, uriVariables, headers, responseType);
    }

    public static <T> T doGet(String url,
            Map<String, Object> uriVariables,
            MultiValueMap<String, String> headers,
            ParameterizedTypeReference<T> responseType
    ) throws RestClientException,
            FileNotFoundException,
            IOException {
        return doRequest(url, HttpMethod.GET, BodyType.FORM,
                null, uriVariables, headers, responseType);
    }

    public static <T> T doDelete(String url,
            Map<String, Object> uriVariables,
            MultiValueMap<String, String> headers,
            ParameterizedTypeReference<T> responseType
    ) throws RestClientException,
            FileNotFoundException,
            IOException {
        return doRequest(url, HttpMethod.DELETE, BodyType.FORM,
                null, uriVariables, headers, responseType);
    }

    public static <T> T doPut(String url,
            BodyType bodyType,
            Map<String, Object> bodyVariables,
            Map<String, Object> uriVariables,
            MultiValueMap<String, String> headers,
            ParameterizedTypeReference<T> responseType
    ) throws RestClientException,
            FileNotFoundException,
            IOException {
        return doRequest(url, HttpMethod.PUT, bodyType,
                bodyVariables, uriVariables, headers, responseType);
    }

    private static JsonResponse parseMap(Map<String, Object> map) {
        Object code = map.get("code");
        if (!(code instanceof Integer)) return null;
        Object desc = map.get("desc");
        if (!(desc instanceof String)) return null;
        log.debug("desc: [" + desc.getClass().getName() +
                "] " + desc.toString());
        Object message = map.get("message");
        if (!(message instanceof String)) return null;
        Object data = map.get("data");
        if (null != data && !(data instanceof Serializable)) return null;
        return new JsonResponse((Integer)code, (String)message,
                (String)desc, (Serializable)data);
    }

    public static JsonResponse myPost(String url,
            BodyType bodyType,
            Map<String, Object> bodyVariables,
            Map<String, Object> uriVariables,
            MultiValueMap<String, String> headers
    ) throws RestClientException,
            FileNotFoundException,
            IOException {
        return parseMap(doPost(url, bodyType, bodyVariables, uriVariables,
                headers, mapType));
    }

    public static JsonResponse myPatch(String url,
            BodyType bodyType,
            Map<String, Object> bodyVariables,
            Map<String, Object> uriVariables,
            MultiValueMap<String, String> headers
    ) throws RestClientException,
            FileNotFoundException,
            IOException {
        return parseMap(doPatch(url, bodyType, bodyVariables, uriVariables,
                headers, mapType));
    }

    public static JsonResponse myGet(String url,
            Map<String, Object> uriVariables,
            MultiValueMap<String, String> headers
    ) throws RestClientException,
            FileNotFoundException,
            IOException {
        return parseMap(doGet(url, uriVariables, headers, mapType));
    }

    public static JsonResponse myDelete(String url,
            Map<String, Object> uriVariables,
            MultiValueMap<String, String> headers
    ) throws RestClientException,
            FileNotFoundException,
            IOException {
        return parseMap(doDelete(url, uriVariables, headers, mapType));
    }

    public static JsonResponse myPut(String url,
            BodyType bodyType,
            Map<String, Object> bodyVariables,
            Map<String, Object> uriVariables,
            MultiValueMap<String, String> headers
    ) throws RestClientException,
            FileNotFoundException,
            IOException {
        return parseMap(doPut(url, bodyType, bodyVariables, uriVariables,
                headers, mapType));
    }

}
