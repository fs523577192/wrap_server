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
import org.firas.common.validator.UrlValidator;
import org.firas.common.validator.ChineseMobileValidator;
import org.firas.sms.model.App;
import org.firas.sms.model.AliyunApp;
import org.firas.sms.service.AppService;

@Controller
@RequestMapping("/sms/app")
public class SmsAppController extends RequestController {

    private AppService appService;
    @Autowired
    public void setAppService(AppService appService) {
        this.appService = appService;
    }

    @RequestMapping(method=RequestMethod.POST)
    public @ResponseBody JsonResponse createAction(
        @RequestParam(required=false) String provider,
        @RequestParam(required=false) String name,
        @RequestParam(required=false) String url,
        @RequestParam(value="app_id", required=false) String appId,
        @RequestParam(value="app_secret", required=false) String appSecret,
        @RequestParam(value="end_point", required=false) String endPoint,
        @RequestParam(value="operator_id", required=false) String operatorId,
        @RequestParam(value="operator_ip", required=false) String operatorIp,
        @RequestParam(value="user_agent", required=false) String userAgent
    ) {
        MutableInteger operatorUserId = new MutableInteger();
        JsonResponseInvalidInput result = validateCreateInput(
                provider, name, operatorId, operatorUserId);
        if (null != result) return result;

        App app = appService.getAppByName(name);
        if (null != app) return new JsonResponseNameOccupied(
                "该短信应用的名称已被占用", "该短信应用的名称已被占用", name);

        if ("aliyun".equalsIgnoreCase(provider)) {
            return createAliyunApp(name, url, appId, appSecret, endPoint,
                    operatorIp, operatorUserId.getValue(), userAgent);
        }
        return new JsonResponseFailUndefined(
                "该运营商不被支持", "该运营商不被支持", provider);
    }

    private JsonResponse createAliyunApp(String name, String url,
            String appId, String appSecret, String endPoint,
            String operatorIp, Integer operatorId, String userAgent) {
        JsonResponseInvalidInput result = validateCreateAliyunInput(
                url, appId, appSecret, endPoint);
        if (null != result) return result;

        AliyunApp aliyunApp = new AliyunApp(name, endPoint,
                appId, appSecret, url);

        try {
            aliyunApp = appService.createAliyunApp(aliyunApp,
                    operatorIp, userAgent, operatorId);
            return new JsonResponseSuccess("创建短信应用成功", "创建短信应用成功",
                    aliyunApp.getMapForLog());
        } catch (JsonProcessingException ex) {
            return dealWithInternalException(ex, "创建短信应用失败");
        }
    }

    private JsonResponseInvalidInput validateCreateInput(
            String provider, String name,
            String operatorId, MutableInteger operatorUserId) {
        Map<String, InputValidation> inputs = this.checkOperatorId(operatorId);
        inputs.put("provider", new InputValidation(provider,
                new StringValidator(null, null, 1, "没有指定运营商")));
        inputs.put("name", new InputValidation(name,
                new StringValidator(64, "短信应用名称最多64个字符",
                        2, "短信应用名称最少2个字符")));
        JsonResponseInvalidInput result = this.validateInput(inputs, true);
        if (null == result) {
            this.getOperatorUserId(inputs, operatorId, operatorUserId);
        }
        return result;
    }

    private JsonResponseInvalidInput validateCreateAliyunInput(
            String url, String appId, String appSecret, String endPoint) {
        HashMap<String, InputValidation> inputs =
                new HashMap<String, InputValidation>();
        inputs.put("url", new InputValidation(url,
                new StringValidator(null, null, 1, "没有URL")));
        inputs.put("app_id", new InputValidation(appId,
                new StringValidator(null, null, 1, "没有App ID")));
        inputs.put("app_secret", new InputValidation(appSecret,
                new StringValidator(null, null, 1, "没有App Secret")));
        inputs.put("end_point", new InputValidation(appSecret,
                new StringValidator(null, null, 1, "没有指定服务端点")));
        return this.validateInput(inputs, true);
    }

    @RequestMapping(method=RequestMethod.PATCH)
    public @ResponseBody JsonResponse updateAction(
        @RequestParam(required=false) String id,
        @RequestParam(required=false) String provider,
        @RequestParam(required=false) String name,
        @RequestParam(required=false) String url,
        @RequestParam(value="app_id", required=false) String appId,
        @RequestParam(value="app_secret", required=false) String appSecret,
        @RequestParam(value="end_point", required=false) String endPoint,
        @RequestParam(value="operator_id", required=false) String operatorId,
        @RequestParam(value="operator_ip", required=false) String operatorIp,
        @RequestParam(value="user_agent", required=false) String userAgent
    ) {
        MutableInteger idInt = new MutableInteger();
        MutableInteger operatorUserId = new MutableInteger();
        JsonResponseInvalidInput result = validateUpdateInput(
                id, idInt, provider, name, operatorId, operatorUserId);
        if (null != result) return result;

        App app = appService.getAppById(idInt.getValue());
        if (null == app) {
            return new JsonResponseNotFound(
                    "该ID的短信应用不存在",
                    "该ID的短信应用不存在", idInt.getValue());
        }

        HashMap<String, String> change = new HashMap<String, String>();
        if (null != name) {
            App temp = appService.getAppByName(name);
            if (null != temp && temp.getId() != app.getId()) {
                return new JsonResponseNameOccupied(
                        "该短信应用的名称已被占用",
                        "该短信应用的名称已被占用", name);
            }
            change.put("name", name);
            app.setName(name);
        }

        if (null != provider) {
        }
        return new JsonResponseFailUndefined(
                "未实现", "未实现", null);
    }

    private JsonResponseInvalidInput validateUpdateInput(
            String id, MutableInteger appId, String provider, String name,
            String operatorId, MutableInteger operatorUserId) {
        Map<String, InputValidation> inputs = this.checkOperatorId(operatorId);
        inputs.put("id", new InputValidation(id,
                new IntegerValidator("短信应用ID必须是一个正整数", 1, null,
                        "短信应用ID必须是一个正整数",
                        "短信应用ID必须是一个正整数")));
        if (null != provider) {
            inputs.put("provider", new InputValidation(provider,
                    new StringValidator(null, null, 1, "没有指定运营商")));
        }
        if (null != name) {
            inputs.put("name", new InputValidation(name,
                    new StringValidator(64, "短信应用名称最多64个字符",
                            2, "短信应用名称最少2个字符")));
        }
        JsonResponseInvalidInput result = this.validateInput(inputs, true);
        if (null == result) {
            appId.setValue((Integer)inputs.get("id").getNewValue());
            this.getOperatorUserId(inputs, operatorId, operatorUserId);
        }
        return result;
    }

    private JsonResponseInvalidInput validateUpdateAliyunInput(
            String url, String appId, String appSecret, String endPoint) {
        HashMap<String, InputValidation> inputs =
                new HashMap<String, InputValidation>();
        if (null != url) {
            inputs.put("url", new InputValidation(url,
                    new StringValidator(null, null, 1, "没有URL")));
        }
        if (null != appId) {
            inputs.put("app_id", new InputValidation(appId,
                    new StringValidator(null, null, 1, "没有App ID")));
        }
        if (null != appSecret) {
            inputs.put("app_secret", new InputValidation(appSecret,
                    new StringValidator(null, null, 1, "没有App Secret")));
        }
        if (null != endPoint) {
            inputs.put("end_point", new InputValidation(appSecret,
                    new StringValidator(null, null, 1, "没有指定服务端点")));
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
        MutableInteger appId = new MutableInteger();
        MutableInteger operatorUserId = new MutableInteger();
        JsonResponseInvalidInput result = validateGetByIdInput(
                id, appId, operatorId, operatorUserId);
        if (null != result) return result;

        App app = appService.getAppById(appId.getValue());
        if (null == app) {
            return new JsonResponseNotFound(
                    "该ID的短信应用不存在",
                    "该ID的短信应用不存在", appId.getValue());
        }

        return new JsonResponseSuccess("",
                    "按ID查询短信应用成功", app.getMapForLog());
    }

    private JsonResponseInvalidInput validateGetByIdInput(
            String id, MutableInteger appId,
            String operatorId, MutableInteger operatorUserId) {
        HashMap<String, InputValidation> inputs =
                new HashMap<String, InputValidation>();
        inputs.put("id", new InputValidation(id,
                new IntegerValidator("短信应用ID必须是一个正整数", 1, null,
                        "短信应用ID必须是一个正整数",
                        "短信应用ID必须是一个正整数")));
        if (null != operatorId) {
            inputs.put("operator_id", new InputValidation(operatorId,
                    new IntegerValidator("操作者用户ID必须是一个正整数", 1, null,
                            "操作者用户ID必须是一个正整数",
                            "操作者用户ID必须是一个正整数")));
        }
        JsonResponseInvalidInput result = this.validateInput(inputs, true);
        if (null == result) {
            Object temp = inputs.get("id").getNewValue();
            appId.setValue((Integer)temp);
            if (null != operatorId) {
                operatorUserId.setValue(
                        (Integer)inputs.get("operator_id").getNewValue());
            }
        }
        return result;
    }
}
