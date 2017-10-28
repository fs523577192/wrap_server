package org.firas.jiadian.input;

import java.util.Date;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.firas.common.request.InputValidation;
import org.firas.common.validator.IValidator;
import org.firas.common.validator.ValidationException;
import org.firas.jiadian.entity.Activity;

/**
 *
 */
@NoArgsConstructor
public class ActivityInput {

    @Getter @Setter private String id;

    @Getter @Setter private String name;

    @Getter @Setter private String year;

    @Getter @Setter private String semester;

    @Getter @Setter private String beginTime;

    @Getter @Setter private String endTime;

    @SuppressWarnings("unchecked")
    public Activity toActivity(Map<String, IValidator> validators)
            throws ValidationException {
        Activity result = new Activity();
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
        validateSemester(validators, result);
        validateTime(validators, result);
        return result;
    }

    @SuppressWarnings("unchecked")
    private void validateSemester(Map<String, IValidator> validators,
            Activity result) throws ValidationException {
        IValidator<?> validator = validators.get("year");
        if (null != validator) {
            InputValidation<Integer> validation = new InputValidation<>(
                    year, (IValidator<Integer>)validator);
            if (!validation.validate(true)) {
                throw new ValidationException(validation.getErrors());
            }
            result.setYear(validation.getNewValue().shortValue());
        }
        validator = validators.get("semester");
        if (null != validator) {
            InputValidation<Integer> validation = new InputValidation<>(
                    semester, (IValidator<Integer>)validator);
            if (!validation.validate(true)) {
                throw new ValidationException(validation.getErrors());
            }
            result.setSemester(validation.getNewValue().shortValue());
        }
    }

    @SuppressWarnings("unchecked")
    private void validateTime(Map<String, IValidator> validators,
            Activity result) throws ValidationException {
        IValidator<?> validator = validators.get("beginTime");
        if (null != validator) {
            InputValidation<Date> validation = new InputValidation<>(
                    beginTime, (IValidator<Date>)validator);
            if (!validation.validate(true)) {
                throw new ValidationException(validation.getErrors());
            }
            result.setBeginTime(validation.getNewValue());
        }
        validator = validators.get("endTime");
        if (null != validator) {
            InputValidation<Date> validation = new InputValidation<>(
                    endTime, (IValidator<Date>)validator);
            if (!validation.validate(true)) {
                throw new ValidationException(validation.getErrors());
            }
            result.setEndTime(validation.getNewValue());
        }
    }
}
