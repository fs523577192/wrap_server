package org.firas.common.request;

import lombok.Getter;
import lombok.Setter;

import org.firas.common.datatype.OperatorInput;

public class HasOperatorInput {

    @Getter @Setter private String operatorIp;
    @Getter @Setter private String userAgent;
    @Getter @Setter private String operatorId;

    private static final String ID_MESSAGE = "操作者的用户ID必须是一个正整数";
    private static final IntegerValidator pageValidator = new IntegerValidator(
            PAGE_MESSAGE, 1, null, PAGE_MESSAGE, PAGE_MESSAGE);
    private static final IntegerValidator sizeValidator = new IntegerValidator(
            SIZE_MESSAGE, 1, null, SIZE_MESSAGE, SIZE_MESSAGE);
    @SuppressWarnings("unchecked")
    public OperatorInput toOperatorInput() {
    }
}
