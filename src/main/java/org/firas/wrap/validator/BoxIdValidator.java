package org.firas.wrap.validator;

import org.firas.wrap.entity.Box;
import org.springframework.validation.Errors;

/**
 *
 */
public class BoxIdValidator extends AbstractBoxValidator {

    @Override
    public void validate(Object target, Errors errors) {
        validateId(Box.class.cast(target), errors);
    }
}