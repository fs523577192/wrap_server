package org.firas.sms.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import lombok.extern.slf4j.Slf4j;

import org.firas.sms.model.AliyunTemplate;
import org.firas.sms.model.Sms;
import org.firas.sms.model.Template;
import org.firas.sms.service.AliyunService;
import org.firas.sms.service.TemplateService;
import org.firas.common.request.InputValidation;
import org.firas.common.validator.IntegerValidator;
import org.firas.common.validator.StringValidator;
import org.firas.common.validator.ChineseMobileValidator;
import org.firas.common.controller.RequestController;
import org.firas.common.response.JsonResponse;
import org.firas.common.response.JsonResponseSuccess;
import org.firas.common.response.JsonResponseFailUndefined;
import org.firas.common.response.input.JsonResponseInvalidInput;
import org.firas.common.response.notfound.JsonResponseNotFound;

@RestController
@RequestMapping("/sms/point")
@Slf4j
public class PointExpiredTimeController extends RequestController{

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

    @RequestMapping(value="/expired", method = RequestMethod.POST)
    public JsonResponse smsPointExpiredTimeAction(
            @RequestParam(value="app", required=false) String appName,
            @RequestParam(value="template", required=false) String templateName,
            @RequestParam(value="mobile", required=false) String mobile,
            @RequestParam(value="point", required=false) String point,
            @RequestParam(value="name", required=false) String name,
            @RequestParam(value="current", required=false) String current,
            @RequestParam(value="end", required=false) String end,
            @RequestParam(value="operator_ip", required=false) String operatorIp,
            @RequestParam(value="user_agent", required=false) String userAgent,
            @RequestParam(value="operator_id", required=false) String operatorId
    ) {
        JsonResponseInvalidInput result = validatePointExpiredInput(
                templateName, mobile);
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
        if (template.getProvider().equals(Sms.Provider.ALIYUN)) {
            Integer temp = template.getId();
            AliyunTemplate aliyunTemplate =
                    templateService.getAliyunTemplateByTemplateId(temp);
            if (null == aliyunTemplate) return new JsonResponseFailUndefined(
                    "", "内部逻辑错误：provider为ALIYUN的template" +
                    "没有对应的AliyunTemplate", temp);

            try {
                Map<String, String> map = new HashMap<String, String>();
                map.put("name", name);
                map.put("point", point);
                map.put("current", current);
                map.put("end", end);
                Sms sms = aliyunService.pointExpiredTimeInfo(aliyunTemplate, mobile, map);
                return new JsonResponseSuccess(
                        "发送短信成功", "积分过期时间提醒使用阿里云发送短信成功",
                        sms.getId());
            } catch (Exception ex) {
                return dealWithInternalException(ex, "积分过期时间提醒发送短信失败");
            }
        }
        return new JsonResponseFailUndefined("未实现", "积分过期时间提醒未实现", null);
    }

    private JsonResponseInvalidInput validatePointExpiredInput(
             String template, String mobile) {
        Map<String, InputValidation> inputs = new HashMap<String, InputValidation>();
        inputs.put("mobile", new InputValidation(mobile,
                new ChineseMobileValidator("手机号格式不正确")));
        inputs.put("template", new InputValidation(template,
                new StringValidator(null, null, 1, "没有指定短信模板")));
        JsonResponseInvalidInput result = this.validateInput(inputs, true);
        return result;
    }
}
