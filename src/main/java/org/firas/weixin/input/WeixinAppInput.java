package org.firas.weixin.input;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import org.firas.common.request.InputValidation;
import org.firas.common.validator.IValidator;
import org.firas.common.validator.ValidationException;
import org.firas.weixin.model.WeixinApp;

@NoArgsConstructor
public class WeixinAppInput {

    @Getter @Setter private String id;
    @Getter @Setter private String name;
    @Getter @Setter private String appId;
    @Getter @Setter private String appSecret;

    @SuppressWarnings("unchecked")
    public WeixinApp toWeixinApp(Map<String, IValidator> validators)
            throws ValidationException {
        WeixinApp result = new WeixinApp();
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
        validator = validators.get("app_id");
        if (null != validator) {
            InputValidation<String> validation = new InputValidation<>(
                    appId, (IValidator<String>)validator);
            if (!validation.validate(true)) {
                throw new ValidationException(validation.getErrors());
            }
            result.setAppId(validation.getNewValue());
        }
        validator = validators.get("app_secret");
        if (null != validator) {
            InputValidation<String> validation = new InputValidation<>(
                    appSecret, (IValidator<String>)validator);
            if (!validation.validate(true)) {
                throw new ValidationException(validation.getErrors());
            }
            result.setAppSecret(validation.getNewValue());
        }
        return result;
    }
}
