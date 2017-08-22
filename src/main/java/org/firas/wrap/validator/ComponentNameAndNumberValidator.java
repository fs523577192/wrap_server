package org.firas.wrap.validator;

import org.firas.wrap.entity.Component;
import org.springframework.validation.Errors;

/**
 *
 */
public class ComponentNameAndNumberValidator extends AbstractComponentValidator {

    @Override
    public void validate(Object target, Errors errors) {
        Component component = Component.class.cast(target);
        validateNameAndNumber(component, errors);
    }
}