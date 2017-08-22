package org.firas.wrap.validator;

import org.firas.wrap.entity.Box;
import org.springframework.validation.Errors;

/**
 *
 */
public class BoxValidator extends AbstractBoxValidator {

    @Override
    public void validate(Object target, Errors errors) {
        Box box = Box.class.cast(target);
        validateId(box, errors);
        validateName(box, errors);
    }
}