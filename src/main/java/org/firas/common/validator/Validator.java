package org.firas.common.validator;

import java.util.List;
import java.util.LinkedList;
import lombok.Getter;
import lombok.Setter;

public abstract class Validator implements IValidator {

    @Getter protected boolean onlyOneError;
    @Getter protected List<ValidationError> errors;

    public Validator() {
        onlyOneError = false;
        errors = new LinkedList<ValidationError>();
    }

    public IValidator setOnlyOneError(boolean onlyOneError) {
        this.onlyOneError = onlyOneError;
        return this;
    }

    public boolean convertType() {
        return false;
    }

    public Object getConverted() {
        return null;
    }

}
