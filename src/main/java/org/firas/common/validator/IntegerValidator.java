package org.firas.common.validator;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IntegerValidator extends Validator<Integer> {

    @Getter @Setter protected Integer min, max;
    @Getter @Setter protected String message, messageMin, messageMax;
    @Getter protected Integer converted;

    protected static final String
            DEFAULT_MESSAGE = "The input is not a string of an integer",
            DEFAULT_MESSAGE_MIN = "The input is less than the minimum",
            DEFAULT_MESSAGE_MAX = "The input is greater than the maximum";

    public static final String
            CODE = "Integer",
            CODE_MIN = "Integer.min",
            CODE_MAX = "Integer.max";

    protected String code = CODE,
            codeMin = CODE_MIN,
            codeMax = CODE_MAX;


    public IntegerValidator() {
        this(null, null);
    }

    public IntegerValidator(String message) {
        this(message, null, null);
    }

    public IntegerValidator(Integer min, Integer max) {
        this(DEFAULT_MESSAGE, min, max);
    }

    public IntegerValidator(String message, Integer min, Integer max) {
        this(message, min, max, DEFAULT_MESSAGE_MIN, DEFAULT_MESSAGE_MAX);
    }

    public IntegerValidator(String message, Integer min, Integer max,
            String messageMin, String messageMax) {
        this.message = message;
        this.min = min;
        this.max = max;
        this.messageMin = messageMin;
        this.messageMax = messageMax;
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
            converted = Integer.parseInt(str);
            if (null != min && converted < min) {
                this.errors.add(new ValidationError(codeMin, messageMin));
                log.debug(codeMin);
                if (this.onlyOneError) return false;
                result = false;
            }
            if (null != max && converted > max) {
                this.errors.add(new ValidationError(codeMax, messageMax));
                log.debug(codeMax);
                return false;
            }
            return result;
        } catch (NullPointerException e) {
        } catch (NumberFormatException e) {
        }
        this.errors.add(new ValidationError(code, message));
        return false;
    }

}
