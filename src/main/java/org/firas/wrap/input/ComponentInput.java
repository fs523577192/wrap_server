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

    @Getter @Setter private String unit;

    @SuppressWarnings("unchecked")
    public Component toComponent(Map<String, IValidator> validators)
            throws ValidationException {
        Component result = new Component();
        IValidator validator = validators.get("id");
        if (null != validator) {
            InputValidation<Integer> validation = new InputValidation<>(
                    id, (IValidator<Integer>)validator);
            if (!validation.validate(true)) {
                throw new ValidationException(validation.getErrors());
            }
            result.setId(validation.getNewValue());
        }
        validator = validators.get("name");
        if (null != validator) {
            InputValidation<String> validation = new InputValidation<>(
                    name, (IValidator<String>)validator);
            if (!validation.validate(true)) {
                throw new ValidationException(validation.getErrors());
            }
            result.setName(validation.getNewValue());
        }
        validator = validators.get("number");
        if (null != validator) {
            InputValidation<Integer> validation = new InputValidation<>(
                    number, (IValidator<Integer>)validator);
            if (!validation.validate(true)) {
                throw new ValidationException(validation.getErrors());
            }
            result.setNumber(validation.getNewValue());
        }
        validator = validators.get("unit");
        if (null != validator) {
            InputValidation<String> validation = new InputValidation<>(
                    unit, (IValidator<String>)validator);
            if (!validation.validate(true)) {
                throw new ValidationException(validation.getErrors());
            }
            result.setUnit(validation.getNewValue());
        }
        return result;
    }
}
