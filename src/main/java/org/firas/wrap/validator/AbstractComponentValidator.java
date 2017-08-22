package org.firas.wrap.validator;

import org.firas.wrap.entity.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 */
public abstract class AbstractComponentValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Component.class.isAssignableFrom(clazz);
    }

    protected static void validateId(Component Component, Errors errors) {
        if (null == Component.getId() || Component.getId() <= 0) {
            errors.rejectValue("id", "field.min",
                    "零部件的ID必须是一个正整数");
        }
    }

    protected static void validateNameAndNumber(Component component, Errors errors) {
        if (null == component.getName()) {
            errors.rejectValue("name", "field.minLength",
                    "零部件的名称至少要有一个字符（首尾的空格不计算字符数）");
        }
        if (component.getNumber() < Component.NUMBER_MIN) {
            errors.rejectValue("number", "field.min",
                    "零部件的数量必须是一个非负整数");
        }
    }
}