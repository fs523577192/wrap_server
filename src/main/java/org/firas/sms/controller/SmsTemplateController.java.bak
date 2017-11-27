package org.firas.sms.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;

import org.firas.common.datatype.MutableInteger;
import org.firas.common.controller.RequestController;
import org.firas.common.response.JsonResponse;
import org.firas.common.response.JsonResponseSuccess;
import org.firas.common.response.JsonResponseFailUndefined;
import org.firas.common.response.input.JsonResponseInvalidInput;
import org.firas.common.response.notfound.JsonResponseNotFound;
import org.firas.common.response.occupied.JsonResponseNameOccupied;
import org.firas.common.request.InputValidation;
import org.firas.common.validator.StringValidator;
import org.firas.common.validator.IntegerValidator;
import org.firas.common.validator.ChineseMobileValidator;
import org.firas.sms.model.Template;
import org.firas.sms.model.AliyunTemplate;
import org.firas.sms.model.App;
import org.firas.sms.model.AliyunApp;
import org.firas.sms.model.Sms;
import org.firas.sms.service.AppService;
import org.firas.sms.service.TemplateService;

@Controller
@RequestMapping("/sms/template")
@Slf4j
public class SmsTemplateController extends RequestController {

    private TemplateService templateService;
    @Autowired
    public void setTemplateService(TemplateService templateService) {
        this.templateService = templateService;
    }

    private AppService appService;
    @Autowired
    public void setAppService(AppService appService) {
        this.appService = appService;
    }

    @RequestMapping(method=RequestMethod.POST)
    public @ResponseBody JsonResponse createAction(
        @RequestParam(value="name", required=false) String name,
        @RequestParam(value="app_id", required=false) String appId,
        @RequestParam(value="code", required=false) String code,
        @RequestParam(value="sign_name", required=false) String signName,
        @RequestParam(value="operator_id", required=false) String operatorId,
        @RequestParam(value="operator_ip", required=false) String operatorIp,
        @RequestParam(value="user_agent", required=false) String userAgent
    ) {
        MutableInteger operatorUserId = new MutableInteger();
        MutableInteger appIdInt = new MutableInteger();
        JsonResponseInvalidInput result = validateCreateInput(
                appId, appIdInt, name, operatorId, operatorUserId);
        if (null != result) return result;

        App app = appService.getAppById(appIdInt.getValue());
        if (null == app) return new JsonResponseNotFound(
                "该ID的短信应用不存在",
                "该ID的短信应用不存在", appIdInt.getValue());

        Template template = templateService.getTemplateByAppIdAndName(
                appIdInt.getValue(), name);
        if (null != template) {
            HashMap<String, Object> data = new HashMap<String, Object>();
            data.put("app_id", appIdInt.getValue());
            data.put("name", name);
            return new JsonResponseNameOccupied(
                    "该短信应用已有一个该名称的模板",
                    "该短信应用已有一个该名称的模板", data);
        }

        if (app.getProvider() == Sms.Provider.ALIYUN) {
            return createAliyunTemplate(app, name, signName, code,
                    operatorIp, operatorUserId.getValue(), userAgent);
        }
        String desc = "逻辑错误：非法的短信应用运营商";
        return new JsonResponseFailUndefined(desc, desc, null);
    }

    private JsonResponse createAliyunTemplate(
            App app, String name, String signName, String code,
            String operatorIp, Integer operatorId, String userAgent) {
        JsonResponseInvalidInput result = validateCreateAliyunInput(
                code, signName);
        if (null != result) return result;

        AliyunTemplate aliyunTemplate = new AliyunTemplate(
                app, name, signName, code);

        try {
            aliyunTemplate = templateService.createAliyunTemplate(
                    aliyunTemplate, operatorIp, userAgent, operatorId);
            return new JsonResponseSuccess("创建短信模板成功", "创建短信模板成功",
                    aliyunTemplate.getMapForLog());
        } catch (JsonProcessingException ex) {
            return dealWithInternalException(ex, "创建短信模板失败");
        }
    }

    private JsonResponseInvalidInput validateCreateInput(
            String appId, MutableInteger appIdInt, String name,
            String operatorId, MutableInteger operatorUserId) {
        Map<String, InputValidation> inputs = this.checkOperatorId(operatorId);
        inputs.put("app_id", new InputValidation(appId,
                new IntegerValidator("短信应用的ID必须是一个正整数", 1, null,
                        "短信应用的ID必须是一个正整数",
                        "短信应用的ID必须是一个正整数")));
        inputs.put("name", new InputValidation(name,
                new StringValidator(64, "短信应用名称最多64个字符",
                        2, "短信应用名称最少2个字符")));
        JsonResponseInvalidInput result = this.validateInput(inputs, true);
        if (null == result) {
            appIdInt.setValue(
                    (Integer)inputs.get("app_id").getNewValue());
            this.getOperatorUserId(inputs, operatorId, operatorUserId);
        }
        return result;
    }

    private JsonResponseInvalidInput validateCreateAliyunInput(
            String code, String signName) {
        HashMap<String, InputValidation> inputs =
                new HashMap<String, InputValidation>();
        inputs.put("code", new InputValidation(code,
                new StringValidator(null, null, 1, "没有模板代码")));
        inputs.put("sign_name", new InputValidation(signName,
                new StringValidator(null, null, 1, "没有短信签名")));
        return this.validateInput(inputs, true);
    }

    @RequestMapping(method=RequestMethod.PATCH)
    public @ResponseBody JsonResponse updateAction(
        @RequestParam(value="id", required=false) String id,
        @RequestParam(value="name", required=false) String name,
        @RequestParam(value="code", required=false) String code,
        @RequestParam(value="sign_name", required=false) String signName,
        @RequestParam(value="operator_id", required=false) String operatorId,
        @RequestParam(value="operator_ip", required=false) String operatorIp,
        @RequestParam(value="user_agent", required=false) String userAgent
    ) {
        MutableInteger templateId = new MutableInteger();
        MutableInteger operatorUserId = new MutableInteger();
        JsonResponseInvalidInput result = validateUpdateInput(
                id, templateId, name, operatorId, operatorUserId);
        if (null != result) return result;

        Template template = templateService.getTemplateById(
                templateId.getValue());
        if (null == template) return new JsonResponseNotFound(
                "该ID的短信模板不存在",
                "该ID的短信模板不存在", templateId.getValue());

        Map<String, String> change = new HashMap<String, String>();
        if (null != name) {
            change.put("name", name);
            template.setName(name);
        }
        if (template.getProvider() == Sms.Provider.ALIYUN) {
            return updateAliyunTemplate(template, change, code, signName,
                    operatorIp, userAgent, operatorUserId.getValue());
        }
        return new JsonResponseFailUndefined(
                "", "未实现", null);
    }

    private JsonResponse updateAliyunTemplate(Template template,
            Map<String, String> change, String code, String signName,
            String operatorIp, String userAgent, Integer operatorId) {
        JsonResponseInvalidInput result = validateUpdateAliyunInput(
                code, signName);
        if (null != result) return result;

        AliyunTemplate aliyunTemplate = templateService
                .getAliyunTemplateByTemplateId(template.getId());
        if (null == aliyunTemplate) return new JsonResponseFailUndefined(
                "", "内部逻辑错误：provider为ALIYUN的template" +
                "没有对应的AliyunTemplate", template.getId());

        if (null != code) {
            change.put("code", code);
            aliyunTemplate.setCode(code);
        }
        if (null != signName) {
            log.debug("sign_name=" + signName);
            change.put("sign_name", signName);
            aliyunTemplate.setSignName(signName);
        }
        try {
            aliyunTemplate = templateService.updateAliyunTemplate(
                    aliyunTemplate, template, change,
                    operatorIp, userAgent, operatorId);
            return new JsonResponseSuccess("修改短信模板成功",
                    "修改短信模板成功", aliyunTemplate.getMapForLog());
        } catch (JsonProcessingException ex) {
            return dealWithInternalException(ex, "修改短信模板失败");
        }
    }

    private JsonResponseInvalidInput validateUpdateInput(
            String id, MutableInteger templateId, String name,
            String operatorId, MutableInteger operatorUserId) {
        Map<String, InputValidation> inputs = this.checkOperatorId(operatorId);
        inputs.put("id", new InputValidation(id,
                new IntegerValidator("短信模板的ID必须是一个正整数", 1, null,
                        "短信模板的ID必须是一个正整数",
                        "短信模板的ID必须是一个正整数")));
        if (null != name) {
            inputs.put("name", new InputValidation(name,
                    new StringValidator(64, "短信应用名称最多64个字符",
                            2, "短信应用名称最少2个字符")));
        }
        JsonResponseInvalidInput result = this.validateInput(inputs, true);
        if (null == result) {
            templateId.setValue(
                    (Integer)inputs.get("id").getNewValue());
            this.getOperatorUserId(inputs, operatorId, operatorUserId);
        }
        return result;
    }

    private JsonResponseInvalidInput validateUpdateAliyunInput(
            String code, String signName) {
        HashMap<String, InputValidation> inputs =
                new HashMap<String, InputValidation>();
        if (null != code) {
            inputs.put("code", new InputValidation(code,
                    new StringValidator(null, null, 1, "没有模板代码")));
        }
        if (null != signName) {
            inputs.put("sign_name", new InputValidation(signName,
                    new StringValidator(null, null, 1, "没有短信签名")));
        }
        return this.validateInput(inputs, true);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public @ResponseBody JsonResponse getByIdAction(
        @PathVariable String id,
        @RequestParam(value="operator_id", required=false) String operatorId,
        @RequestParam(value="operator_ip", required=false) String operatorIp,
        @RequestParam(value="user_agent", required=false) String userAgent
    ) {
        MutableInteger templateId = new MutableInteger();
        MutableInteger operatorUserId = new MutableInteger();
        JsonResponseInvalidInput result = validateGetByIdInput(
                id, templateId, operatorId, operatorUserId);
        if (null != result) return result;

        Template template = templateService.getTemplateById(
                templateId.getValue());
        if (null != template) {
            return new JsonResponseNotFound(
                    "该ID的短信模板不存在",
                    "该ID的短信模板不存在", templateId.getValue());
        }

        return new JsonResponseSuccess("",
                    "按ID查询短信模板成功", template.getMapForLog());
    }

    private JsonResponseInvalidInput validateGetByIdInput(
            String id, MutableInteger templateId,
            String operatorId, MutableInteger operatorUserId) {
        HashMap<String, InputValidation> inputs =
                new HashMap<String, InputValidation>();
        inputs.put("id", new InputValidation(id,
                new IntegerValidator("短信模板ID必须是一个正整数", 1, null,
                        "短信模板ID必须是一个正整数",
                        "短信模板ID必须是一个正整数")));
        if (null != operatorId) {
            inputs.put("operator_id", new InputValidation(operatorId,
                    new IntegerValidator("操作者用户ID必须是一个正整数", 1, null,
                            "操作者用户ID必须是一个正整数",
                            "操作者用户ID必须是一个正整数")));
        }
        JsonResponseInvalidInput result = this.validateInput(inputs, true);
        if (null == result) {
            Object temp = inputs.get("id").getNewValue();
            templateId.setValue((Integer)temp);
            if (null != operatorId) {
                operatorUserId.setValue(
                        (Integer)inputs.get("operator_id").getNewValue());
            }
        }
        return result;
    }
}
