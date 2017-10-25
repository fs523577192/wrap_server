package org.firas.sms.controller;

import java.util.Random;
import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;

import org.firas.common.datatype.MutableInteger;
import org.firas.common.helper.RandomHelper;
import org.firas.common.controller.RequestController;
import org.firas.common.response.JsonResponse;
import org.firas.common.response.JsonResponseSuccess;
import org.firas.common.response.JsonResponseFailUndefined;
import org.firas.common.response.input.JsonResponseInvalidInput;
import org.firas.common.response.notfound.JsonResponseNotFound;
import org.firas.common.response.expired.JsonResponseSmsExpired;
import org.firas.common.response.toofrequent.JsonResponseSmsTooFrequent;
import org.firas.common.response.auth.JsonResponseWrongCode;
import org.firas.common.request.InputValidation;
import org.firas.common.validator.IntegerValidator;
import org.firas.common.validator.StringValidator;
import org.firas.common.validator.ChineseMobileValidator;

import org.firas.sms.service.SmsService;
import org.firas.sms.service.AliyunService;
import org.firas.sms.service.TemplateService;
import org.firas.sms.model.Sms;
import org.firas.sms.model.SmsLimit;
import org.firas.sms.model.Template;
import org.firas.sms.model.AliyunTemplate;

@RestController
@RequestMapping("/sms/verification")
@Slf4j
public class VerificationController extends RequestController {

    private static final DateFormat DEFAULT_FORMATTER =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
    
    public static final int VALID_MINUTE = 10;
    private Random random = new Random();

    private SmsService smsService;
    @Autowired
    public void setSmsService(SmsService smsService) {
        this.smsService = smsService;
    }

    private AliyunService aliyunService;
    @Autowired
    public void setAliyunService(AliyunService aliyunService) {
        this.aliyunService = aliyunService;
    }

    private TemplateService templateService;
    @Autowired
    public void setTemplateService(TemplateService templateService) {
        this.templateService = templateService;
    }

    @RequestMapping(method=RequestMethod.POST)
    public JsonResponse sendAction(
        @RequestParam(value="mobile", required=false) String mobile,
        @RequestParam(value="app", required=false) String appName,
        @RequestParam(value="template", required=false) String templateName,
        @RequestParam(value="operator_ip", required=false) String operatorIp,
        @RequestParam(value="user_agent", required=false) String userAgent,
        @RequestParam(value="operator_id", required=false) String operatorId
    ) {
        MutableInteger operatorUserId = new MutableInteger();
        JsonResponseInvalidInput result = validateSendInput(
                mobile, appName, templateName, operatorId, operatorUserId);
        if (null != result) return result;

        Template template = templateService.getTemplateByAppNameAndName(
                appName, templateName);
        if (null == template) {
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("app", appName);
            data.put("template", templateName);
            return new JsonResponseNotFound("该短信模板不存在",
                    "该短信模板不存在", data);
        }

        SmsLimit limit = smsService.checkPrevious(template, mobile);
        if (null != limit) {
            Map<String, Object> options = new HashMap<String, Object>();
            return new JsonResponseSmsTooFrequent(limit.getMessage(),
                    "在" + limit.descPeriod(null, null, null) +
                    "内只能向同一手机号发" + limit.getMaxCount() +
                    "条短信", limit.toMap(options));
        }

        HashMap<String, String> content = new HashMap<String, String>();
        HashMap<String, Object> extra = new HashMap<String, Object>();
        checkPrevious(template, mobile, content, extra);

        if (template.getProvider().equals(Sms.Provider.ALIYUN)) {
            Integer temp = template.getId();
            AliyunTemplate aliyunTemplate =
                    templateService.getAliyunTemplateByTemplateId(temp);
            if (null == aliyunTemplate) return new JsonResponseFailUndefined(
                    "", "内部逻辑错误：provider为ALIYUN的template" +
                    "没有对应的AliyunTemplate", temp);

            try {
                Sms sms = aliyunService.sendSms(aliyunTemplate, mobile,
                        content, extra, operatorIp, userAgent,
                        operatorUserId.getValue());
                return new JsonResponseSuccess(
                        "发送短信成功", "使用阿里云发送短信成功",
                        sms.getId());
            } catch (Exception ex) {
                return dealWithInternalException(ex, "发送短信失败");
            }
        }
        return new JsonResponseFailUndefined("未实现", "未实现", null);
    }

    private JsonResponse checkPrevious(Template template, String mobile,
            Map<String, String> content, Map<String, Object> extra) {
        Date now = new Date();
        long nowTime = now.getTime();
        Date previous10Min = new Date(nowTime - 60000 * VALID_MINUTE);
        // TODO: read configuration stored in the database
        List<Sms> smsList = smsService.findByMobileAfter(
                template, mobile, previous10Min);

        ObjectMapper jsonParser = new ObjectMapper();
        HashMap<String, String> contentMap = null;
        HashMap<String, Object> extraMap = null;
        TypeReference<HashMap<String, String>> contentType =
                new TypeReference<HashMap<String, String>>(){};
        TypeReference<HashMap<String, Object>> extraType =
                new TypeReference<HashMap<String, Object>>(){};
        try {
            Iterator<Sms> iterator = smsList.iterator();
            while (iterator.hasNext()) {
                Sms sms = iterator.next();

                contentMap = jsonParser.readValue(
                        sms.getContent(), contentType);
                extraMap = jsonParser.readValue(
                        sms.getExtra(), extraType);
                String code = contentMap.get("code");
                String minute = contentMap.get("minute");
                Object temp = extraMap.get("first_time");
                if (null == code || null == minute || null == temp ||
                        !(temp instanceof String)) continue;

                Date firstTime = DEFAULT_FORMATTER.parse((String)temp);
                if (firstTime.compareTo(previous10Min) < 0) {
                    contentMap = null;
                }
                break;
            }
            if (null == contentMap) {
                content.put("code", RandomHelper.randomNumericString(6));
                content.put("minute", String.valueOf(VALID_MINUTE));
                // TODO: read configuration stored in the database
                extra.put("first_time", DEFAULT_FORMATTER.format(now));
            } else {
                content.putAll(contentMap);
                extra.putAll(extraMap);
            }
            return null;
        } catch (Exception ex) {
            return this.dealWithInternalException(
                    ex, "发送短信验证码时出错");
        }
    }

    private JsonResponseInvalidInput validateSendInput(
            String mobile, String app, String template,
            String operatorId, MutableInteger operatorUserId) {
        Map<String, InputValidation> inputs = this.checkOperatorId(operatorId);
        inputs.put("mobile", new InputValidation(mobile,
                new ChineseMobileValidator("手机号格式不正确")));
        inputs.put("app", new InputValidation(app,
                new StringValidator(null, null, 1, "没有指定短信应用")));
        inputs.put("template", new InputValidation(template,
                new StringValidator(null, null, 1, "没有指定短信模板")));
        JsonResponseInvalidInput result = this.validateInput(inputs, true);
        if (null == result) {
            this.getOperatorUserId(inputs, operatorId, operatorUserId);
        }
        return result;
    }

    @RequestMapping(method=RequestMethod.GET)
    public JsonResponse verifyAction(
        @RequestParam(required=false) String id,
        @RequestParam(required=false) String code,
        @RequestParam(value="operator_ip", required=false) String operatorIp,
        @RequestParam(value="user_agent", required=false) String userAgent,
        @RequestParam(value="operator_id", required=false) String operatorId
    ) {
        MutableInteger smsId = new MutableInteger();
        MutableInteger operatorUserId = new MutableInteger();
        JsonResponseInvalidInput result = validateVerifyInput(
                id, smsId, code, operatorId, operatorUserId);
        if (null != result) return result;

        Sms sms = smsService.getSmsById(smsId.getValue());
        if (null == sms) return new JsonResponseNotFound("该ID的短信不存在",
                        "该ID的短信不存在", smsId.getValue());

        Date now = new Date();
        Timestamp minValidTime = new Timestamp(now.getTime() -
                VALID_MINUTE * 60 * 1000);
        if (sms.getCreateTime().before(minValidTime)) {
            String message = "短信验证码超过" + VALID_MINUTE +
                    "分钟有效期，请重新发送";
            return new JsonResponseSmsExpired(
                    message, message, sms.getCreateTimeInfo(null));
        }

        ObjectMapper jsonParser = new ObjectMapper();
        try {
            HashMap<String, String> contentMap = jsonParser.readValue(
                    sms.getContent(),
                    new TypeReference<HashMap<String, String>>(){});
            if (code.equalsIgnoreCase(contentMap.get("code"))) {
                return new JsonResponseSuccess(
                        "短信验证码正确", "短信验证码正确", true);
            }
            return new JsonResponseWrongCode("短信验证码错误",
                            "短信验证码错误", false);
        } catch (Exception ex) {
            return this.dealWithInternalException(
                    ex, "检验短信验证码时出错");
        }
    }

    private JsonResponseInvalidInput validateVerifyInput(
            String id, MutableInteger smsId, String code,
            String operatorId, MutableInteger operatorUserId) {
        HashMap<String, InputValidation> inputs =
                new HashMap<String, InputValidation>();
        inputs.put("id", new InputValidation(id,
                new IntegerValidator("短信ID必须是一个正整数", 1, null,
                        "短信ID必须是一个正整数",
                        "短信ID必须是一个正整数")));
        inputs.put("code", new InputValidation(code,
                new StringValidator(null, null, 1, "没有验证码")));
        JsonResponseInvalidInput result = this.validateInput(inputs, true);
        if (null == result) {
            smsId.setValue((Integer)inputs.get("id").getNewValue());
        }
        return result;
    }



}
