package org.firas.wrap.validator;

import org.firas.wrap.entity.Box;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 */
public abstract class AbstractBoxValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Box.class.isAssignableFrom(clazz);
    }

    protected static void validateId(Box box, Errors errors) {
        if (null == box.getId() || box.getId() <= 0) {
            errors.rejectValue("id", "field.min",
                    "出货箱的ID必须是一个正整数");
        }
    }

    protected static void validateName(Box box, Errors errors) {
        if (null == box.getName()) {
            errors.rejectValue("name", "field.minLength",
                    "出货箱的名称至少要有一个字符（首尾的空格不计算字符数）");
        }
    }
}