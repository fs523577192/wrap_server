package org.firas.common.request;

import java.util.List;
import java.util.LinkedList;
import org.firas.common.validator.IValidator;
import org.firas.common.validator.ValidationError;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InputValidation<T> {

    @Getter protected String originalValue;
    @Getter protected T newValue;

    protected List<IValidator<T>> validators;
    protected List<ValidationError> errors;

    public InputValidation(String inputValue) {
        originalValue = inputValue;
        validators = new LinkedList<>();
        errors = new LinkedList<>();
    }

    public InputValidation(String inputValue, IValidator<T> validator) {
        this(inputValue);
        addValidator(validator);
    }


    public InputValidation addValidator(IValidator<T> validator) {
        if (null == validator) {
            throw new NullPointerException("\"validator\" cannot be null");
        }
        validators.add(validator);
        return this;
    }

    public List<IValidator<T>> getValidators() {
        return validators;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    public boolean validate(boolean onlyOneError) {
        boolean result = true;
        if (onlyOneError) {
            for (IValidator<T> validator : validators) {
                validator.setOnlyOneError(true);
                if (validator.validate(originalValue)) {
                    newValue = validator.getConverted();
                } else {
                    errors.addAll(validator.getErrors());
                    return false;
                }
            }
        } else {
            for (IValidator<T> validator : validators) {
                if (validator.validate(originalValue)) {
                    newValue = validator.getConverted();
                } else {
                    errors.addAll(validator.getErrors());
                    result = false;
                }
            }
        }
        return result;
    }
}
