package org.firas.common.validator;

import java.util.List;

public interface IValidator {

    public boolean validate(String str);
    public boolean convertType();
    public Object getConverted();
    public List<ValidationError> getErrors();
    public IValidator setOnlyOneError(boolean onlyOneError);
}
