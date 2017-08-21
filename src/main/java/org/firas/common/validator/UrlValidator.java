package org.firas.common.validator;

public class UrlValidator extends StringValidator {

    protected static final String DEFAULT_MESSAGE =
            "The input is not a valid URL";

    public static final String CODE = "URL";
    public static final String PATTERN = "[a-zA-z]+://[^\\s]*";

	public UrlValidator() {
        this(DEFAULT_MESSAGE);
        this.codeMin = this.codeMax = this.codePattern = CODE;
    }

    public UrlValidator(String message) {
        super(null, message, 5, message, "^" + PATTERN + "$", message);
        this.codeMin = this.codeMax = this.codePattern = CODE;
    }

}
