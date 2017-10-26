package org.firas.common.validator;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import lombok.Getter;
import lombok.Setter;

public class StringValidator extends Validator<String> {

	@Getter protected Integer maxLength, minLength;
	protected Pattern pattern;
	@Getter protected String converted = null;

	@Getter @Setter protected String messageMax, messageMin, messagePattern;
    protected static final String
            DEFAULT_MESSAGE_MIN = "The input string is too short",
            DEFAULT_MESSAGE_MAX = "The input string is too long",
            DEFAULT_MESSAGE_PATTERN = "The input string is invalid";

    public static final String
            CODE_MIN = "String.min",
            CODE_MAX = "String.max",
            CODE_PATTERN = "String.pattern";

    protected String codeMin = CODE_MIN,
            codeMax = CODE_MAX,
            codePattern = CODE_PATTERN;


    public StringValidator setMaxLength(Integer maxLength) {
        if (null != maxLength && maxLength < 0) {
            throw new IllegalArgumentException("\"maxLength\" must be " +
                    "positive or zero");
        }
        this.maxLength = maxLength;
        return this;
    }
    public StringValidator setMinLength(Integer minLength) {
        if (null != minLength && minLength < 0) {
            throw new IllegalArgumentException("\"minLength\" must be " +
                    "positive or zero");
        }
        this.minLength = minLength;
        return this;
    }

    public String getPattern() {
        return pattern.pattern();
    }
    public StringValidator setPattern(String pattern) {
        if (null == pattern) this.pattern = null;
        else this.pattern = Pattern.compile(pattern);
        return this;
    }


    public StringValidator(Integer maxLength) {
        this(maxLength, DEFAULT_MESSAGE_MAX);
    }

    public StringValidator(Integer maxLength, String messageMax) {
        this(maxLength, messageMax, null, DEFAULT_MESSAGE_MIN);
    }

    public StringValidator(Integer maxLength, Integer minLength) {
        this(maxLength, DEFAULT_MESSAGE_MAX, minLength, DEFAULT_MESSAGE_MIN);
    }

    public StringValidator(Integer maxLength, String messageMax,
            Integer minLength, String messageMin) {
        this(maxLength, messageMax, minLength, messageMin,
                null, DEFAULT_MESSAGE_PATTERN);
    }

    public StringValidator(String pattern) {
        this(pattern, DEFAULT_MESSAGE_PATTERN);
    }

    public StringValidator(String pattern, String messagePattern) {
        this(null, DEFAULT_MESSAGE_MAX, null, DEFAULT_MESSAGE_MIN,
                pattern, messagePattern);
    }

    public StringValidator(Integer maxLength, String messageMax,
            Integer minLength, String messageMin,
            String pattern, String messagePattern) {
        if (null == maxLength && null == minLength && null == pattern) {
            throw new IllegalArgumentException("\"maxLength\", \"minLength\"" +
                    " and \"pattern\" cannot be all null");
        }
        this.setMaxLength(maxLength);
        this.setMinLength(minLength);
        this.setPattern(pattern);
        this.messageMax = messageMax;
        this.messageMin = messageMin;
        this.messagePattern = messagePattern;
    }


    public boolean validate(String str) {
        boolean result = true;
        if (null != minLength && minLength > 0) {
            if (null == str || str.length() < minLength) {
                this.errors.add(new ValidationError(codeMin, messageMin));
                if (this.onlyOneError) return false;
                result = false;
            }
        }
        if (null != maxLength && null != str && str.length() > maxLength) {
            this.errors.add(new ValidationError(codeMax, messageMax));
            if (this.onlyOneError) return false;
            result = false;
        }
        if (null != pattern) {
            if (null == str || !pattern.matcher(str).find()) {
                this.errors.add(new ValidationError(
                        codePattern, messagePattern));
                return false;
            }
        }
        if (result) {
            converted = str;
        }
        return result;
    }

}
