package org.firas.common.validator;

import java.util.List;

public interface IValidator<T> {

    boolean validate(String str);
    T getConverted();
    List<ValidationError> getErrors();
    IValidator<T> setOnlyOneError(boolean onlyOneError);
}
