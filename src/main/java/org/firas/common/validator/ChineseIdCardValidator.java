package org.firas.common.validator;

public class ChineseIdCardValidator extends StringValidator {

    protected static final String DEFAULT_MESSAGE =
            "The input is not a valid Chinese ID card number";

    public ChineseIdCardValidator() {
        this(DEFAULT_MESSAGE);
    }

    public ChineseIdCardValidator(String message) {
        super(18, message, 15, message, "^([1-9]\\d{5}(18|19|20|21)" +
                "\\d{2}(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])" +
                "\\d{3}[0-9X])|([1-9]\\d{7}" +
                "(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])\\d{3})$", message);
        this.codeMin = this.codeMax = this.codePattern = "ChineseIdCard";
    }

}
