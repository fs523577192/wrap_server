package org.firas.common.validator;

public class EmailValidator extends StringValidator {

    protected static final String DEFAULT_MESSAGE =
            "The input is not a valid Email address";

    public static final String CODE = "Email";
    public static final String PATTERN = "[\\w!#$%&'*+/=?^_`{|}~-]+" +
                "(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@" +
				"(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";

	public EmailValidator() {
        this(DEFAULT_MESSAGE);
        this.codeMin = this.codeMax = this.codePattern = CODE;
    }

    public EmailValidator(String message) {
        super(null, message, 5, message, "^" + PATTERN + "$", message);
        this.codeMin = this.codeMax = this.codePattern = "Email";
    }
}
