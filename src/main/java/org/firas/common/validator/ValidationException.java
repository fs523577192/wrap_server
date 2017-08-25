package org.firas.common.validator;

import lombok.Getter;
import org.firas.common.response.input.JsonResponseInputTooLarge;
import org.firas.common.response.input.JsonResponseInputTooLong;
import org.firas.common.response.input.JsonResponseInputTooShort;
import org.firas.common.response.input.JsonResponseInputTooSmall;
import org.firas.common.response.input.JsonResponseInvalidDate;
import org.firas.common.response.input.JsonResponseInvalidEmail;
import org.firas.common.response.input.JsonResponseInvalidInput;
import org.firas.common.response.input.JsonResponseInvalidMobile;

import java.util.Iterator;

/**
 *
 */
public class ValidationException extends Exception {

    public ValidationException(Iterable<ValidationError> errors) {
        if (null == errors) {
            throw new NullPointerException("\"errors\" cannot be null");
        }
        Iterator<ValidationError> iterator = errors.iterator();
        if (!iterator.hasNext()) {
            throw new IllegalStateException("\"errors\" is empty");
        }
        this.errors = errors;
        this.firstError = iterator.next();
    }

    @Getter private Iterable<ValidationError> errors;

    private ValidationError firstError;

    @Override
    public String getMessage() {
        return firstError.getMessage();
    }

    public JsonResponseInvalidInput toResponse() {
        String errorCode = firstError.getCode();
        // TODO: get the field name
        if (IntegerValidator.CODE_MIN.equals(errorCode) ||
                IntegerListValidator.CODE_MIN.equals(errorCode)) {
            return new JsonResponseInputTooSmall(getMessage(), null);
        }
        if (IntegerValidator.CODE_MAX.equals(errorCode) ||
                IntegerListValidator.CODE_MAX.equals(errorCode)) {
            return new JsonResponseInputTooLarge(getMessage(), null);
        }
        if (StringValidator.CODE_MAX.equals(errorCode) ||
                IntegerListValidator.CODE_MAX_LENGTH.equals(errorCode)) {
            return new JsonResponseInputTooLong(getMessage(), null);
        }
        if (StringValidator.CODE_MIN.equals(errorCode) ||
                IntegerListValidator.CODE_MIN_LENGTH.equals(errorCode)) {
            return new JsonResponseInputTooShort(getMessage(), null);
        }
        if (DateTimeValidator.CODE.equals(errorCode)) {
            return new JsonResponseInvalidDate(getMessage(), null);
        }
        if (EmailValidator.CODE.equals(errorCode)) {
            return new JsonResponseInvalidEmail(getMessage(), null);
        }
        if (ChineseMobileValidator.CODE.equals(errorCode)) {
            return new JsonResponseInvalidMobile(getMessage(), null);
        }
        return new JsonResponseInvalidInput(getMessage(), null);
    }
}
