package org.firas.common.validator;

public class QQValidator extends StringValidator {

    protected static final String DEFAULT_MESSAGE =
            "The input is not a valid QQ number";

	public QQValidator() {
        this(DEFAULT_MESSAGE);
    }

	public QQValidator(String message) {
        super(11, message, 5, message, "^[1-9]\\d{4, 10}$", message);
        this.codeMin = this.codeMax = this.codePattern = "QQ";
	}

}
