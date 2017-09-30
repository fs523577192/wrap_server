package org.firas.common.validator;

import java.util.List;
import java.util.LinkedList;
import lombok.Getter;
import lombok.Setter;

public abstract class Validator<T> implements IValidator<T> {

    @Getter protected boolean onlyOneError;

    protected List<ValidationError> errors;

    public Validator() {
        onlyOneError = false;
        errors = new LinkedList<>();
    }

    @Override
    public IValidator<T> setOnlyOneError(boolean onlyOneError) {
        this.onlyOneError = onlyOneError;
        return this;
    }

    @Override
    public List<ValidationError> getErrors() {
        return errors;
    }

    public T getConverted() {
        return null;
    }

}
