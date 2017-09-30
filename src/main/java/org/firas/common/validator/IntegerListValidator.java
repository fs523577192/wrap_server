package org.firas.common.validator;

import java.util.List;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

public class IntegerListValidator extends Validator<List<Integer>> {

    @Getter @Setter
    protected Integer min, max, minLength, maxLength;

    @Getter @Setter
    protected String message,
                    messageMin, messageMax,
                    messageMinLength, messageMaxLength;

    @Getter protected List<Integer> converted;

    protected static final String
            DEFAULT_MESSAGE = "The input is not a list of integers",
            DEFAULT_MESSAGE_MIN = "An integer in the list is less than the minimum",
            DEFAULT_MESSAGE_MAX = "An integer in the list is greater than the maximum",
            DEFAULT_MESSAGE_MIN_LENGTH = "The list has more items than expected",
            DEFAULT_MESSAGE_MAX_LENGTH = "The list has fewer items than expected";

    public static final String
            CODE = "IntegerList",
            CODE_MIN = "IntegerList.min",
            CODE_MAX = "IntegerList.max",
            CODE_MIN_LENGTH = "IntegerList.minLength",
            CODE_MAX_LENGTH = "IntegerList.maxLength";

    protected String code = CODE,
            codeMin = CODE_MIN,
            codeMax = CODE_MAX,
            codeMinLength = CODE_MIN_LENGTH,
            codeMaxLength = CODE_MAX_LENGTH;


    public IntegerListValidator() {
        this(null, null);
    }

    public IntegerListValidator(String message) {
        this(message, null, null);
    }

    public IntegerListValidator(Integer min, Integer max) {
        this(DEFAULT_MESSAGE, min, max);
    }

    public IntegerListValidator(String message, Integer min, Integer max) {
        this(message, min, max, DEFAULT_MESSAGE_MIN, DEFAULT_MESSAGE_MAX);
    }

    public IntegerListValidator(String message,
            Integer min, Integer max,
            String messageMin, String messageMax) {
        this(message, min, max, messageMin, messageMax,
                null, null, null, null);
    }

    public IntegerListValidator(String message,
            Integer min, Integer max,
            String messageMin, String messageMax,
            Integer minLength, Integer maxLength,
            String messageMinLength, String messageMaxLength) {
        this.message = message;
        this.min = min;
        this.max = max;
        this.messageMin = messageMin;
        this.messageMax = messageMax;
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.messageMinLength = messageMinLength;
        this.messageMaxLength = messageMaxLength;
        this.converted = null;
    }

    /**
     * Checks whether str represents an integer
     * (optionally with minimum or maximum constraints)
     *
     * @param  str  the string to be validated
     * @return boolean  true if str represents an integer,
     *                  false if the str is invalid
     */
    public boolean validate(String str) {
        try {
            boolean result = true;
            List<Integer> list = new ArrayList<Integer>();
            if (!str.isEmpty()) {
                String[] array = str.split(",", -1);
                for (int i = 0; i < array.length; ++i) {
                    Integer temp = Integer.parseInt(array[i]);
                    list.add(temp);
                    if (null != min && temp < min) {
                        this.errors.add(new ValidationError(
                                codeMin, messageMin));
                        if (this.onlyOneError) return false;
                        result = false;
                    }
                    if (null != max && temp > max) {
                        this.errors.add(new ValidationError(
                                codeMax, messageMax));
                        if (this.onlyOneError) return false;
                        result = false;
                    }
                }
            }
            if (null != minLength && list.size() < minLength) {
                this.errors.add(new ValidationError(
                        codeMinLength, messageMinLength));
                if (this.onlyOneError) return false;
                result = false;
            }
            if (null != maxLength && list.size() > maxLength) {
                this.errors.add(new ValidationError(
                        codeMaxLength, messageMaxLength));
                return false;
            }
            converted = list;
            return result;
        } catch (NullPointerException e) {
        } catch (NumberFormatException e) {
        }
        this.errors.add(new ValidationError(code, message));
        return false;
    }

}
