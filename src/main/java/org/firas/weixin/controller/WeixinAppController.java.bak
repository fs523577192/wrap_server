package org.firas.weixin.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

import org.firas.weixin.datatype.AppNameOccupiedException;
import org.firas.weixin.datatype.AppNotFoundException;
import org.firas.weixin.model.WeixinApp;
import org.firas.weixin.service.WeixinAppService;

@RestController
@RequestMapping("/weixin/app")
public class WeixinAppController extends RequestController {

    private WeixinAppService appService;
    @Autowired
    public void setWeixinAppService(WeixinAppService appService) {
        this.appService = appService;
    }

    @RequestMapping(method=RequestMethod.POST)
    public JsonResponse createAction(
        @RequestParam(required=false) String name,
        @RequestParam(value="app_id", required=false) String appId,
        @RequestParam(value="app_secret", required=false) String appSecret,
        @RequestParam(value="operator_id", required=false) String operatorId,
        @RequestParam(value="operator_ip", required=false) String operatorIp,
        @RequestParam(value="user_agent", required=false) String userAgent
    ) {
        MutableInteger operatorUserId = new MutableInteger();
        JsonResponseInvalidInput result = validateCreateInput(
                name, appId, appSecret, operatorId, operatorUserId);
        if (null != result) return result;

        HashMap<String, Object> options = new HashMap<String, Object>();
        options.put("id", true);
        try {
            WeixinApp app = appService.createWeixinApp(name, appId, appSecret,
                    operatorIp, userAgent, operatorUserId.getValue());
            return new JsonResponseSuccess(
                    "创建微信应用成功", "创建微信应用成功",
                    app.toMap(options));
        } catch(AppNameOccupiedException ex) {
            return new JsonResponseNameOccupied(
                "该微信应用的名称已被占用", "该微信应用的名称已被占用",
                ex.getApp().toMap(options));
        } catch(Exception ex) {
            return this.dealWithInternalException(ex, "创建微信应用失败");
        }
    }

    private JsonResponseInvalidInput validateCreateInput(
            String name, String appId, String appSecret,
            String operatorId, MutableInteger operatorUserId) {
        Map<String, InputValidation> inputs = this.checkOperatorId(operatorId);
        inputs.put("name", new InputValidation(name,
                new StringValidator(WeixinApp.NAME_MAX_LENGTH,
                        "微信应用名称最多" + WeixinApp.NAME_MAX_LENGTH + "个字符",
                        WeixinApp.NAME_MIN_LENGTH,
                        "微信应用名称最少" + WeixinApp.NAME_MIN_LENGTH + "个字符")));
        inputs.put("app_id", new InputValidation(appId,
                new StringValidator(WeixinApp.APP_ID_MAX_LENGTH,
                        "AppID最多" + WeixinApp.APP_ID_MAX_LENGTH + "个字符",
                        WeixinApp.APP_ID_MIN_LENGTH,
                        "AppID最少" + WeixinApp.APP_ID_MIN_LENGTH + "个字符")));
        inputs.put("app_secret", new InputValidation(appSecret,
                new StringValidator(WeixinApp.SECRET_MAX_LENGTH,
                        "AppSecret最多" + WeixinApp.SECRET_MAX_LENGTH + "个字符",
                        WeixinApp.SECRET_MIN_LENGTH,
                        "AppSecret最少" + WeixinApp.SECRET_MIN_LENGTH + "个字符")));
        JsonResponseInvalidInput result = this.validateInput(inputs, true);
        if (null == result) {
            this.getOperatorUserId(inputs, operatorId, operatorUserId);
        }
        return result;
    }

    @RequestMapping(method=RequestMethod.PATCH)
    public JsonResponse updateAction(
        @RequestParam(required=false) String id,
        @RequestParam(required=false) String name,
        @RequestParam(value="app_id", required=false) String appId,
        @RequestParam(value="app_secret", required=false) String appSecret,
        @RequestParam(value="operator_id", required=false) String operatorId,
        @RequestParam(value="operator_ip", required=false) String operatorIp,
        @RequestParam(value="user_agent", required=false) String userAgent
    ) {
        MutableInteger idInt = new MutableInteger();
        MutableInteger operatorUserId = new MutableInteger();
        JsonResponseInvalidInput result = validateUpdateInput(id, idInt,
                name, appId, appSecret, operatorId, operatorUserId);
        if (null != result) return result;

        HashMap<String, Object> options = new HashMap<String, Object>();
        options.put("id", true);
        try {
            WeixinApp app = appService.updateWeixinApp(idInt.getValue(),
                    name, appId, appSecret, operatorIp, userAgent,
                    operatorUserId.getValue());
            return new JsonResponseSuccess(
                    "修改微信应用成功", "修改微信应用成功",
                    app.toMap(options));
        } catch(AppNotFoundException ex) {
            return new JsonResponseNotFound("该ID的微信应用不存在",
                    "该ID的微信应用不存在", idInt.getValue());
        } catch(AppNameOccupiedException ex) {
            return new JsonResponseNameOccupied(
                "该微信应用的名称已被占用", "该微信应用的名称已被占用",
                ex.getApp().toMap(options));
        } catch(Exception ex) {
            return this.dealWithInternalException(ex, "修改微信应用失败");
        }
    }

    private JsonResponseInvalidInput validateUpdateInput(
            String strId, MutableInteger id,
            String name, String appId, String appSecret,
            String operatorId, MutableInteger operatorUserId) {
        Map<String, InputValidation> inputs = this.checkOperatorId(operatorId);
        inputs.put("id", new InputValidation(strId,
                new IntegerValidator("微信应用ID必须是一个正整数",
                        1, null, "微信应用ID必须是一个正整数",
                        "微信应用ID必须是一个正整数")));
        if (null != name) {
            inputs.put("name", new InputValidation(name,
                    new StringValidator(WeixinApp.NAME_MAX_LENGTH,
                            "微信应用名称最多" + WeixinApp.NAME_MAX_LENGTH + "个字符",
                            WeixinApp.NAME_MIN_LENGTH,
                            "微信应用名称最少" + WeixinApp.NAME_MIN_LENGTH + "个字符")));
        }
        if (null != appId) {
            inputs.put("app_id", new InputValidation(appId,
                    new StringValidator(WeixinApp.APP_ID_MAX_LENGTH,
                            "AppID最多" + WeixinApp.APP_ID_MAX_LENGTH + "个字符",
                            WeixinApp.APP_ID_MIN_LENGTH,
                            "AppID最少" + WeixinApp.APP_ID_MIN_LENGTH + "个字符")));
        }
        if (null != appSecret) {
            inputs.put("app_secret", new InputValidation(appSecret,
                    new StringValidator(WeixinApp.SECRET_MAX_LENGTH,
                            "AppSecret最多" + WeixinApp.SECRET_MAX_LENGTH + "个字符",
                            WeixinApp.SECRET_MIN_LENGTH,
                            "AppSecret最少" + WeixinApp.SECRET_MIN_LENGTH + "个字符")));
        }
        JsonResponseInvalidInput result = this.validateInput(inputs, true);
        if (null == result) {
            id.setValue((Integer)inputs.get("id").getNewValue());
            this.getOperatorUserId(inputs, operatorId, operatorUserId);
        }
        return result;
    }

    @RequestMapping(value="/id", method=RequestMethod.GET)
    public JsonResponse getByIdAction(
        @RequestParam(required=false) String id,
        @RequestParam(value="operator_id", required=false) String operatorId,
        @RequestParam(value="operator_ip", required=false) String operatorIp,
        @RequestParam(value="user_agent", required=false) String userAgent
    ) {
        MutableInteger idInt = new MutableInteger();
        MutableInteger operatorUserId = new MutableInteger();
        JsonResponseInvalidInput result = validateGetByIdInput(
                id, idInt, operatorId, operatorUserId);
        if (null != result) return result;

        HashMap<String, Object> options = new HashMap<String, Object>();
        options.put("id", true);
        try {
            WeixinApp app = appService.getWeixinAppById(idInt.getValue());
            if (null == app) return new JsonResponseNotFound(
                    "该ID的微信应用不存在",
                    "该ID的微信应用不存在", idInt.getValue());
            return new JsonResponseSuccess(
                    "按ID查询微信应用成功", "按ID查询微信应用成功",
                    app.toMap(options));
        } catch (Exception ex) {
            return this.dealWithInternalException(ex,
                    "按ID查询微信应用失败");
        }
    }

    private JsonResponseInvalidInput validateGetByIdInput(
            String strId, MutableInteger id,
            String operatorId, MutableInteger operatorUserId) {
        Map<String, InputValidation> inputs = this.checkOperatorId(operatorId);
        inputs.put("id", new InputValidation(strId,
                new IntegerValidator("微信应用ID必须是一个正整数",
                        1, null, "微信应用ID必须是一个正整数",
                        "微信应用ID必须是一个正整数")));
        JsonResponseInvalidInput result = this.validateInput(inputs, true);
        if (null == result) {
            id.setValue((Integer)inputs.get("id").getNewValue());
            this.getOperatorUserId(inputs, operatorId, operatorUserId);
        }
        return result;
    }

    @RequestMapping(value="/name", method=RequestMethod.GET)
    public JsonResponse getByNameAction(
        @RequestParam(required=false) String name,
        @RequestParam(value="operator_id", required=false) String operatorId,
        @RequestParam(value="operator_ip", required=false) String operatorIp,
        @RequestParam(value="user_agent", required=false) String userAgent
    ) {
        MutableInteger operatorUserId = new MutableInteger();
        JsonResponseInvalidInput result = validateGetByNameInput(
                name, operatorId, operatorUserId);
        if (null != result) return result;

        HashMap<String, Object> options = new HashMap<String, Object>();
        options.put("id", true);
        try {
            WeixinApp app = appService.getWeixinAppByName(name);
            if (null == app) return new JsonResponseNotFound(
                    "该名称的微信应用不存在",
                    "该名称的微信应用不存在", name);
            return new JsonResponseSuccess(
                    "按名称查询微信应用成功", "按名称查询微信应用成功",
                    app.toMap(options));
        } catch (Exception ex) {
            return this.dealWithInternalException(ex,
                    "按名称查询微信应用失败");
        }
    }

    private JsonResponseInvalidInput validateGetByNameInput(
            String name, String operatorId, MutableInteger operatorUserId) {
        Map<String, InputValidation> inputs = this.checkOperatorId(operatorId);
        inputs.put("name", new InputValidation(name,
                new StringValidator(WeixinApp.NAME_MAX_LENGTH,
                        "微信应用名称最多" + WeixinApp.NAME_MAX_LENGTH + "个字符",
                        WeixinApp.NAME_MIN_LENGTH,
                        "微信应用名称最少" + WeixinApp.NAME_MIN_LENGTH + "个字符")));
        JsonResponseInvalidInput result = this.validateInput(inputs, true);
        if (null == result) {
            this.getOperatorUserId(inputs, operatorId, operatorUserId);
        }
        return result;
    }

}
