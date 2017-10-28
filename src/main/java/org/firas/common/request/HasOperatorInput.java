package org.firas.common.request;

import lombok.Getter;
import lombok.Setter;

import org.firas.common.datatype.OperatorInput;
import org.firas.common.validator.IntegerValidator;

public class HasOperatorInput {

    @Getter @Setter private String operatorIp;
    @Getter @Setter private String userAgent;
    @Getter @Setter private String operatorId;

    private static final String ID_MESSAGE = "操作者的用户ID必须是一个正整数";
    private static final IntegerValidator idValidator = new IntegerValidator(
            ID_MESSAGE, 1, null, ID_MESSAGE, ID_MESSAGE);
    /*
    private static final IntegerValidator ipValidator = new IntegerValidator(
            IP_MESSAGE, 1, null, IP_MESSAGE, IP_MESSAGE);
            */

    @SuppressWarnings("unchecked")
    public OperatorInput toOperatorInput() {
        return null;
    }
}
