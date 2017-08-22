package org.firas.wrap.validator;

import org.firas.wrap.entity.Component;
import org.springframework.validation.Errors;

/**
 *
 */
public class ComponentIdValidator extends AbstractComponentValidator {

    @Override
    public void validate(Object target, Errors errors) {
        validateId(Component.class.cast(target), errors);
    }
}