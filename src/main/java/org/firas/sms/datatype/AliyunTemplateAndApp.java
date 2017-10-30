package org.firas.sms.datatype;

import lombok.Getter;
import lombok.AllArgsConstructor;

import org.firas.sms.model.SmsApp;
import org.firas.sms.model.AliyunApp;
import org.firas.sms.model.Template;
import org.firas.sms.model.AliyunTemplate;

@AllArgsConstructor
public class AliyunTemplateAndApp {

    @Getter private SmsApp app;
    @Getter private AliyunApp aliyunApp;
    @Getter private Template template;
    @Getter private AliyunTemplate aliyunTemplate;
}
