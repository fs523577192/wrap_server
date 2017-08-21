package org.firas.common.validator;

public class WeakChineseIdCardValidator extends StringValidator {

    protected static final String DEFAULT_MESSAGE =
            "The input is not a valid Chinese ID card number";

    public WeakChineseIdCardValidator() {
        this(DEFAULT_MESSAGE);
    }

    public WeakChineseIdCardValidator(String message) {
        super(18, message, 15, message, "^[1-9]\\d{14}" +
                "(\\d{2}[0-9X])?$", message);
        this.codeMin = this.codeMax = this.codePattern = "ChineseIdCard";
    }

}
