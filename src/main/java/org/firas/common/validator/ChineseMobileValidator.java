package org.firas.common.validator;

public class ChineseMobileValidator extends StringValidator {

    protected static final String DEFAULT_MESSAGE =
            "The input is not a valid Chinese mobile phone number";

    public static final String CODE = "Mobile";

    public ChineseMobileValidator() {
        this(DEFAULT_MESSAGE);
    }

	public ChineseMobileValidator(String message) {
        super(11, message, 11, message, "^1\\d{10}$", message);
        this.codeMin = this.codeMax = this.codePattern = "ChineseMobile";
	}

}
