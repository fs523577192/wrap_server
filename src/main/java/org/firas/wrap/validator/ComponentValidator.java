package org.firas.wrap.validator;

import org.firas.wrap.entity.Component;
import org.springframework.validation.Errors;

/**
 *
 */
public class ComponentValidator extends AbstractComponentValidator {

    @Override
    public void validate(Object target, Errors errors) {
        Component component = Component.class.cast(target);
        validateId(component, errors);
        validateNameAndNumber(component, errors);
    }
}