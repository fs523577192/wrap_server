package org.firas.sms.datatype;

public class TemplateNotFoundException extends RuntimeException {

    public TemplateNotFoundException() {
        this("要查找的短信模板不存在");
    }

    public TemplateNotFoundException(String message) {
        super(message);
    }

}
