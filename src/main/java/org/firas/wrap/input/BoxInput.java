package org.firas.wrap.input;

import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.firas.common.request.InputValidation;
import org.firas.common.validator.IValidator;
import org.firas.common.validator.ValidationException;
import org.firas.wrap.entity.Box;

/**
 *
 */
@NoArgsConstructor
public class BoxInput {

    @Getter @Setter private String id;

    @Getter @Setter private String name;

    public Box toBox(Map<String, IValidator> validators)
            throws ValidationException {
        Box result = new Box();
        IValidator validator = validators.get("id");
        if (null != validator) {
            InputValidation validation = new InputValidation(id, validator);
            if (!validation.validate(true)) {
                throw new ValidationException(validation.getErrors());
            }
            result.setId(Integer.class.cast(validation.getNewValue()));
        }
        validator = validators.get("name");
        if (null != validator) {
            InputValidation validation = new InputValidation(name, validator);
            if (!validation.validate(true)) {
                throw new ValidationException(validation.getErrors());
            }
            result.setName(validation.getNewValue().toString());
        }
        return result;
    }
}
