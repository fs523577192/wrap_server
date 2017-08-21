package org.firas.common.validator;

public class ChineseValidator extends StringValidator {

    protected static final String DEFAULT_MESSAGE =
            "The input is not Chinese";

    public static final String CODE = "Chinese";
    public static final String PATTERN = "[\\u3400-\\u4DBF" +
            "\\u4E00-\\u9FFF" + "\\uF900-\\uFAFF" +
            "\\u20000-\\u2A6D6" + "\\u2A700-\\u2B734" +
            "\\u2B820-\\u2CEA1" + "\\u2F800-\\u2FA1F]";

	public ChineseValidator() {
        this(DEFAULT_MESSAGE);
        this.codeMin = this.codeMax = this.codePattern = CODE;
    }

    public ChineseValidator(String message) {
        super(null, message, 1, message, "^" + PATTERN + "$", message);
        this.codeMin = this.codeMax = this.codePattern = "Chinese";
    }
}
