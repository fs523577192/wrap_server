package org.firas.wrap.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.firas.common.request.InputValidation;
import org.firas.common.validator.IValidator;
import org.firas.common.validator.ValidationException;
import org.firas.wrap.entity.Component;

import java.util.Map;

/**
 *
 */
@NoArgsConstructor
public class ComponentInput {

    @Getter @Setter private String id;

    @Getter @Setter private String name;

    @Getter @Setter private String number;

    public Component toComponent(Map<String, IValidator> validators)
            throws ValidationException {
        Component result = new Component();
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
        validator = validators.get("number");
        if (null != validator) {
            InputValidation validation = new InputValidation(number, validator);
            if (!validation.validate(true)) {
                throw new ValidationException(validation.getErrors());
            }
            result.setNumber(Integer.class.cast(validation.getNewValue()));
        }
        return result;
    }
}
