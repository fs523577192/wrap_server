package org.firas.wrap.input;

import java.util.List;
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

    @Getter @Setter private String components;

    @SuppressWarnings("unchecked")
    public Box toBox(Map<String, IValidator> validators)
            throws ValidationException {
        Box result = new Box();
        IValidator validator = validators.get("id");
        if (null != validator) {
            InputValidation<Integer> validation = new InputValidation<>(
                    id, (IValidator<Integer>)validator);
            if (!validation.validate(true)) {
                throw new ValidationException(validation.getErrors());
            }
            result.setId(Integer.class.cast(validation.getNewValue()));
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
        validator = validators.get("components");
        if (null != validator) {
            InputValidation<Map<Integer, Integer>> validation = new InputValidation<>(
                    components, (IValidator<Map<Integer, Integer>>)validator);
            if (!validation.validate(true)) {
                throw new ValidationException(validation.getErrors());
            }
            Map<Integer, Integer> temp = validation.getNewValue();
            for (Map.Entry<Integer, Integer> entry : temp.entrySet()) {
                Integer componentId = entry.getKey();
                Integer number = entry.getValue();
            }
        }
        return result;
    }
}
