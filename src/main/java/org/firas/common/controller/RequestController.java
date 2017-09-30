package org.firas.common.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Date;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.firas.common.datatype.MutableInteger;
import org.firas.common.request.InputValidation;
import org.firas.common.validator.IValidator;
import org.firas.common.validator.ValidationError;
import org.firas.common.validator.FieldValidationError;
import org.firas.common.validator.IntegerValidator;
import org.firas.common.validator.IntegerListValidator;
import org.firas.common.validator.FloatValidator;
import org.firas.common.validator.StringValidator;
import org.firas.common.validator.EmailValidator;
import org.firas.common.validator.ChineseMobileValidator;
import org.firas.common.validator.DateTimeValidator;
import org.firas.common.response.input.JsonResponseInvalidInput;
import org.firas.common.response.input.JsonResponseInvalidMobile;
import org.firas.common.response.input.JsonResponseInvalidEmail;
import org.firas.common.response.input.JsonResponseInvalidDate;
import org.firas.common.response.input.JsonResponseInputTooLarge;
import org.firas.common.response.input.JsonResponseInputTooSmall;
import org.firas.common.response.input.JsonResponseInputTooLong;
import org.firas.common.response.input.JsonResponseInputTooShort;

@Slf4j
public abstract class RequestController extends ResponseController {

    protected String getRequestIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (null == ip || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("proxy-client-ip");
        }
        if (null == ip || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("wl-proxy-client-ip");
        }
        if (null == ip || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-real-ip");
        }
        if (null == ip || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("http_client_ip");
        }
        if (null == ip || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("http_x_forwarded_for");
        }
        if (null == ip || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (null == ip || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteHost();
        }
        return ip;
    }

    protected String getUserAgent(HttpServletRequest request) {
        return request.getHeader("user-agent");
    }

    protected boolean isFromBrowser(HttpServletRequest request) {
        String userAgent = getUserAgent(request);
        return null != userAgent && userAgent.contains("Mozilla");
    }

    protected boolean isFromWeixinBrowser(HttpServletRequest request) {
        String userAgent = getUserAgent(request);
        return null != userAgent && userAgent.contains("MicroMessenger");
    }

    protected boolean isFromAndroid(HttpServletRequest request) {
        String userAgent = getUserAgent(request);
        return null != userAgent && userAgent.contains("Android");
    }

    protected boolean isFromIos(HttpServletRequest request) {
        String userAgent = getUserAgent(request);
        return null != userAgent && (userAgent.contains("iPhone") ||
                userAgent.contains("iPad"));
    }


    protected JsonResponseInvalidInput validateInput(
            Map<String, InputValidation<?>> inputs, boolean onlyOneError) {
        JsonResponseInvalidInput result = null;
        if (onlyOneError) {
            for (Map.Entry<String, InputValidation<?>> entry :
                    inputs.entrySet()) {
                InputValidation<?> validation = entry.getValue();
                if (!validation.validate(true)) {
                    List<ValidationError> errors = validation.getErrors();
                    if (errors.size() < 1) {
                        return new JsonResponseInvalidInput(null);
                    }
                    FieldValidationError errorMap = new FieldValidationError();
                    errorMap.put(entry.getKey(), errors.get(0));
                    return getInvalidInputFromError(errors.get(0), errorMap);
                }
            }
        } else {
            for (Map.Entry<String, InputValidation<?>> entry :
                    inputs.entrySet()) {
                InputValidation validation = entry.getValue();
                if (!validation.validate(false)) {
                    // Not implemented yet
                }
            }
        }
        return result;
    }

    private JsonResponseInvalidInput getInvalidInputFromError(
            ValidationError error, FieldValidationError errorMap) {
        String code = error.getCode();
        log.debug(code);
        String message = error.getMessage();
        if (code.equals(IntegerValidator.CODE_MAX) ||
                code.equals(FloatValidator.CODE_MAX) ||
                code.equals(IntegerListValidator.CODE_MAX)) {
            return new JsonResponseInputTooLarge(message, message, errorMap);
        }
        if (code.equals(IntegerValidator.CODE_MIN) ||
                code.equals(FloatValidator.CODE_MIN) ||
                code.equals(IntegerListValidator.CODE_MIN)) {
            return new JsonResponseInputTooSmall(message, message, errorMap);
        }
        if (code.equals(StringValidator.CODE_MAX) ||
                code.equals(IntegerListValidator.CODE_MAX_LENGTH)) {
            return new JsonResponseInputTooLong(message, message, errorMap);
        }
        if (code.equals(StringValidator.CODE_MIN) ||
                code.equals(IntegerListValidator.CODE_MIN_LENGTH)) {
            return new JsonResponseInputTooShort(message, message, errorMap);
        }
        if (code.equals(DateTimeValidator.CODE)) {
            return new JsonResponseInvalidDate(message, message, errorMap);
        }
        if (code.equals(ChineseMobileValidator.CODE)) {
            return new JsonResponseInvalidMobile(message, message, errorMap);
        }
        if (code.equals(EmailValidator.CODE)) {
            return new JsonResponseInvalidEmail(message, message, errorMap);
        }
        return new JsonResponseInvalidInput(message, message, errorMap);
    }

}
