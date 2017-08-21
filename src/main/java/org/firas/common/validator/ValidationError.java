package org.firas.common.validator;

import lombok.Getter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ValidationError {

    @Getter protected String code;
    @Getter protected String message;
}
