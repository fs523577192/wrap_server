package org.firas.jiadian.input;

import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.firas.common.request.InputValidation;
import org.firas.common.validator.IValidator;
import org.firas.common.validator.ValidationException;
import org.firas.jiadian.entity.Activity;
import org.firas.jiadian.entity.Order;

/**
 *
 */
@NoArgsConstructor
public class OrderInput {

    @Getter @Setter private String id;

    @Getter @Setter private String activityId;

    @Getter @Setter private String owner;

    @Getter @Setter private String mobile;

    @Getter @Setter private String phone;

    @Getter @Setter private String email;

    @Getter @Setter private String schoolIdCode;

    @Getter @Setter private String name;

    @Getter @Setter private String worker;

    @Getter @Setter private String reason;

    @SuppressWarnings("unchecked")
    public Order toOrder(Map<String, IValidator> validators)
            throws ValidationException {
        Order result = new Order();
        IValidator validator = validators.get("id");
        if (null != validator) {
            InputValidation<Integer> validation = new InputValidation<>(
                    id, (IValidator<Integer>)validator);
            if (!validation.validate(true)) {
                throw new ValidationException(validation.getErrors());
            }
            result.setId(validation.getNewValue());
        }
        validator = validators.get("activityId");
        if (null != validator) {
            InputValidation<Integer> validation = new InputValidation<>(
                    activityId, (IValidator<Integer>)validator);
            if (!validation.validate(true)) {
                throw new ValidationException(validation.getErrors());
            }
            result.setActivity(new Activity(validation.getNewValue()));
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
        validateOwner(validators, result);
        validateWork(validators, result);
        return result;
    }

    @SuppressWarnings("unchecked")
    private void validateOwner(Map<String, IValidator> validators,
            Order result) throws ValidationException {
        IValidator<?> validator = validators.get("owner");
        if (null != validator) {
            InputValidation<String> validation = new InputValidation<>(
                    owner, (IValidator<String>)validator);
            if (!validation.validate(true)) {
                throw new ValidationException(validation.getErrors());
            }
            result.setOwner(validation.getNewValue());
        }
        validator = validators.get("mobile");
        if (null != validator) {
            InputValidation<String> validation = new InputValidation<>(
                    mobile, (IValidator<String>)validator);
            if (!validation.validate(true)) {
                throw new ValidationException(validation.getErrors());
            }
            result.setMobile(validation.getNewValue());
        }
        validator = validators.get("phone");
        if (null != validator) {
            InputValidation<String> validation = new InputValidation<>(
                    phone, (IValidator<String>)validator);
            if (!validation.validate(true)) {
                throw new ValidationException(validation.getErrors());
            }
            result.setPhone(validation.getNewValue());
        }
        validator = validators.get("email");
        if (null != validator) {
            InputValidation<String> validation = new InputValidation<>(
                    email, (IValidator<String>)validator);
            if (!validation.validate(true)) {
                throw new ValidationException(validation.getErrors());
            }
            result.setEmail(validation.getNewValue());
        }
        validator = validators.get("schoolIdCode");
        if (null != validator) {
            InputValidation<String> validation = new InputValidation<>(
                    schoolIdCode, (IValidator<String>)validator);
            if (!validation.validate(true)) {
                throw new ValidationException(validation.getErrors());
            }
            result.setSchoolIdCode(validation.getNewValue());
        }
    }

    @SuppressWarnings("unchecked")
    private void validateWork(Map<String, IValidator> validators,
            Order result) throws ValidationException {
        IValidator<?> validator = validators.get("worker");
        if (null != validator) {
            InputValidation<String> validation = new InputValidation<>(
                    worker, (IValidator<String>)validator);
            if (!validation.validate(true)) {
                throw new ValidationException(validation.getErrors());
            }
            result.setWorker(validation.getNewValue());
        }
        validator = validators.get("reason");
        if (null != validator) {
            InputValidation<String> validation = new InputValidation<>(
                    reason, (IValidator<String>)validator);
            if (!validation.validate(true)) {
                throw new ValidationException(validation.getErrors());
            }
            result.setReason(validation.getNewValue());
        }
    }
}
