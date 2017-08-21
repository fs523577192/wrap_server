package org.firas.common.request;

import java.util.List;
import java.util.LinkedList;
import org.firas.common.validator.IValidator;
import org.firas.common.validator.ValidationError;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InputValidation {

    @Getter protected String originalValue;
    @Getter protected Object newValue;
    @Getter protected List<IValidator> validators;
    @Getter protected List<ValidationError> errors;

    public InputValidation(String inputValue) {
        originalValue = inputValue;
        validators = new LinkedList<IValidator>();
        errors = new LinkedList<ValidationError>();
    }

    public InputValidation(String inputValue, IValidator validator) {
        this(inputValue);
        addValidator(validator);
    }


    public InputValidation addValidator(IValidator validator) {
        validators.add(validator);
        return this;
    }

    public boolean validate(boolean onlyOneError) {
        boolean result = true;
        if (onlyOneError) {
            for (IValidator validator : validators) {
                validator.setOnlyOneError(true);
                if (validator.validate(originalValue)) {
                    newValue = validator.convertType() ?
                            validator.getConverted() : originalValue;
                    newValue = null == newValue ? originalValue : newValue;
                } else {
                    errors.addAll(validator.getErrors());
                    return false;
                }
            }
        } else {
            for (IValidator validator : validators) {
                if (validator.validate(originalValue)) {
                    newValue = validator.convertType() ?
                            validator.getConverted() : originalValue;
                } else {
                    errors.addAll(validator.getErrors());
                    result = false;
                }
            }
        }
        return result;
    }
}
