package org.firas.sms.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import com.aliyun.mns.client.*;
import com.aliyun.mns.model.*;
import com.aliyun.mns.common.ServiceException;

import org.firas.common.datatype.Triple;
import org.firas.sms.model.AliyunApp;
import org.firas.sms.model.App;
import org.firas.sms.model.AliyunTemplate;
import org.firas.sms.model.Template;
import org.firas.sms.model.Sms;
import org.firas.sms.service.AppService;
import org.firas.sms.service.SmsService;
import org.firas.sms.datatype.AliyunTemplateAndApp;
import org.firas.sms.datatype.SendFrequentlyException;

@Slf4j
@Service
public class AliyunService {

    private AppService appService;
    @Autowired
    public void setAppService(AppService appService) {
        this.appService = appService;
    }

    private SmsService smsService;
    @Autowired
    public void setSmsService(SmsService smsService) {
        this.smsService = smsService;
    }

    public AliyunTemplateAndApp getAliyunAppByAliyunTemplate(
            AliyunTemplate aliyunTemplate) throws Exception {
        Template template = aliyunTemplate.getTemplate();
        App app = template.getApp();
        AliyunApp aliyunApp = appService.getAliyunAppById(app.getId());
        if (null == aliyunApp) throw new Exception("The AliyunTemplate does not " +
                "have a corresponding AliyunApp");
        return new AliyunTemplateAndApp(app, aliyunApp,
                template, aliyunTemplate);
    }

    public Sms sendSms(
            AliyunTemplate aliyunTemplate, String mobile,
            Map<String, String> contentMap, Map<String, Object> extraMap,
            String ip, String userAgent, Integer operatorId
    ) throws Exception {
        Triple<MNSClient, CloudTopic, AliyunTemplateAndApp> triple = getMNSClientAndCloudTopic(aliyunTemplate);
        RawTopicMessage msg = getRawTopicMessage();
        MessageAttributes messageAttributes = getMessageAttributes(
                aliyunTemplate, mobile, contentMap);
        try {
            TopicMessage ret = triple.getItem2().publishMessage(msg, messageAttributes);
            log.debug("MessageId: " + ret.getMessageId());
            log.debug("MessageMD5: " + ret.getMessageBodyMD5());
            return smsService.createSms(
                    triple.getItem3().getTemplate(), mobile,
                    new ObjectMapper().writeValueAsString(contentMap),
                    new ObjectMapper().writeValueAsString(extraMap),
                    ip, userAgent, operatorId);
        } catch (ServiceException se) {
            log.warn(se.getErrorCode() + se.getRequestId());
            log.warn(se.getMessage());
            throw se;
        } finally {
            triple.getItem1().close();
        }
    }

    private Triple<MNSClient, CloudTopic, AliyunTemplateAndApp> getMNSClientAndCloudTopic(
            AliyunTemplate aliyunTemplate) throws Exception {
        AliyunTemplateAndApp info = getAliyunAppByAliyunTemplate(aliyunTemplate);
        AliyunApp aliyunApp = info.getAliyunApp();

        CloudAccount account = new CloudAccount(
                aliyunApp.getAppId(), aliyunApp.getAppSecret(),
                aliyunApp.getUrl());
        MNSClient client = account.getMNSClient();
        CloudTopic topic = client.getTopicRef(aliyunApp.getEndPoint()); // Topic
        return new Triple<>(client, topic, info);
    }

    private RawTopicMessage getRawTopicMessage() {
        RawTopicMessage msg = new RawTopicMessage();
        msg.setMessageBody("sms-message");
        return msg;
    }

    private BatchSmsAttributes getSmsAttributes(AliyunTemplate aliyunTemplate) {
        BatchSmsAttributes batchSmsAttributes = new BatchSmsAttributes();
        // 设置发送短信的签名（SMSSignName）
        batchSmsAttributes.setFreeSignName(aliyunTemplate.getSignName());
        // 设置发送短信使用的模板（SMSTempateCode）
        batchSmsAttributes.setTemplateCode(aliyunTemplate.getCode());
        return batchSmsAttributes;
    }

    private MessageAttributes getMessageAttributes(
            AliyunTemplate aliyunTemplate,
            String mobile, Map<String, String> contentMap) {
        MessageAttributes messageAttributes = new MessageAttributes();
        BatchSmsAttributes batchSmsAttributes = getSmsAttributes(aliyunTemplate);
        // 3.3 设置发送短信所使用的模板中参数对应的值（在短信模板中定义的，没有可以不用设置）
        BatchSmsAttributes.SmsReceiverParams smsReceiverParams = new BatchSmsAttributes.SmsReceiverParams();
        for (Map.Entry<String, String> entry : contentMap.entrySet()) {
            smsReceiverParams.setParam(entry.getKey(), entry.getValue());
        }
        // 3.4 增加接收短信的号码
        batchSmsAttributes.addSmsReceiver(mobile, smsReceiverParams);
        messageAttributes.setBatchSmsAttributes(batchSmsAttributes);
        return messageAttributes;
    }


    public Sms pointExpiredTimeInfo (
            AliyunTemplate aliyunTemplate, String mobile,
            Map<String, String> contentMap
    ) throws Exception{
        Triple<MNSClient, CloudTopic, AliyunTemplateAndApp> triple = getMNSClientAndCloudTopic(aliyunTemplate);
        RawTopicMessage msg = getRawTopicMessage();
        MessageAttributes messageAttributes = getMessageAttributes(
                aliyunTemplate, mobile, contentMap);
        try {
            TopicMessage ret = triple.getItem2().publishMessage(msg, messageAttributes);
            log.debug("MessageId: " + ret.getMessageId());
            log.debug("MessageMD5: " + ret.getMessageBodyMD5());
            return smsService.createSms(
                    triple.getItem3().getTemplate(), mobile,
                    new ObjectMapper().writeValueAsString(contentMap),
                    null,
                    null, null, null);
        } catch (ServiceException se) {
            log.warn(se.getErrorCode() + se.getRequestId());
            log.warn(se.getMessage());
            throw se;
        } finally {
            triple.getItem1().close();
        }
    }
}
