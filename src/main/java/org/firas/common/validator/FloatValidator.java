package org.firas.common.validator;

import lombok.Getter;
import lombok.Setter;

public class FloatValidator extends Validator<Float> {

    @Getter @Setter protected Float min, max;
    @Getter @Setter protected boolean includeMin, includeMax;
    @Getter @Setter protected String message, messageMin, messageMax;
    @Getter protected Float converted;

    protected static final String
            DEFAULT_MESSAGE = "The input is not a string of a " +
                    "single-precision floating point number",
            DEFAULT_MESSAGE_MIN = "The input is less than the minimum",
            DEFAULT_MESSAGE_MAX = "The input is greater than the maximum";

    public static final String
            CODE = "Float",
            CODE_MIN = "Float.min",
            CODE_MAX = "Float.max";

    protected String code = CODE,
            codeMin = CODE_MIN,
            codeMax = CODE_MAX;


    public FloatValidator() {
        this(null, null);
    }

    public FloatValidator(String message) {
        this(message, null, null, false, false);
    }

    public FloatValidator(Float min, Float max) {
        this(min, max, false, false);
    }

    public FloatValidator(Float min, Float max,
            boolean includeMin, boolean includeMax) {
        this(DEFAULT_MESSAGE, min, max, includeMin, includeMax);
    }

    public FloatValidator(String message, Float min, Float max,
            boolean includeMin, boolean includeMax) {
        this(message, min, max, includeMin, includeMax,
                DEFAULT_MESSAGE_MIN, DEFAULT_MESSAGE_MAX);
    }

    public FloatValidator(String message, Float min, Float max,
            boolean includeMin, boolean includeMax,
            String messageMin, String messageMax) {
        this.message = message;
        this.min = min;
        this.max = max;
        this.includeMin = includeMin;
        this.includeMax = includeMax;
        this.messageMin = messageMin;
        this.messageMax = messageMax;
        this.converted = null;
    }

    /**
     * Checks whether str represents a sinle-precision floating point number
     * (optionally with minimum or maximum constraints)
     *
     * @param  str  the string to be validated
     * @return boolean  true if str represents a floating point number,
     *                  false if the str is invalid
     */
    public boolean validate(String str) {
        try {
            boolean result = true;
            converted = Float.parseFloat(str);
            if (null != min) {
                if (converted < min || (!includeMin && converted == min)) {
                    this.errors.add(new ValidationError(codeMin, messageMin));
                    if (this.onlyOneError) return false;
                    result = false;
                }
            }
            if (null != max) {
                if (converted > max || (!includeMax && converted == max)) {
                    this.errors.add(new ValidationError(codeMax, messageMax));
                    return false;
                }
            }
            return result;
        } catch (NullPointerException | NumberFormatException e) {
        }
        this.errors.add(new ValidationError(code, message));
        return false;
    }

}
