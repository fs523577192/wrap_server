package org.firas.common.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.firas.common.validator.IntegerValidator;
import org.firas.common.validator.ValidationException;
import org.springframework.data.domain.PageRequest;

/**
 *
 */
@NoArgsConstructor
@AllArgsConstructor
public class PageInput {

    @Getter @Setter private String page;
    @Getter @Setter private String size;

    private static final String PAGE_MESSAGE = "页码必须是一个正整数";
    private static final String SIZE_MESSAGE = "每页项目数必须是一个正整数";
    private static final IntegerValidator pageValidator = new IntegerValidator(
            PAGE_MESSAGE, 1, null, PAGE_MESSAGE, PAGE_MESSAGE);
    private static final IntegerValidator sizeValidator = new IntegerValidator(
            SIZE_MESSAGE, 1, null, SIZE_MESSAGE, SIZE_MESSAGE);

    public PageRequest toPageRequest(boolean onlyOneError)
            throws ValidationException {
        InputValidation<Integer> pageValidation = new InputValidation<>(page, pageValidator);
        InputValidation<Integer> sizeValidation = new InputValidation<>(size, sizeValidator);
        if (!pageValidation.validate(onlyOneError)) {
            throw new ValidationException(pageValidation.getErrors());
        }
        if (!sizeValidation.validate(onlyOneError)) {
            throw new ValidationException(sizeValidation.getErrors());
        }
        int pageValue = pageValidation.getNewValue();
        int sizeValue = sizeValidation.getNewValue();
        return new PageRequest(pageValue - 1, sizeValue);
    }
}
