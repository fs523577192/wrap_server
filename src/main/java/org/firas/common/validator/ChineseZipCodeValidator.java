package org.firas.common.validator;

public class ChineseZipCodeValidator extends StringValidator {

    protected static final String DEFAULT_MESSAGE =
            "The input is not a valid Chinese zip code";

    public ChineseZipCodeValidator() {
        this(DEFAULT_MESSAGE);
    }

	public ChineseZipCodeValidator(String message) {
        super(9, message, 6, message, "^[1-9]\\d{5}(?!\\d)$", message);
        this.codeMin = this.codeMax = this.codePattern = "ChineseZipCode";
	}

}
