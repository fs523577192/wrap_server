package org.firas.weixin.controller;

import java.util.HashMap;
import java.util.Map;
import java.net.URLEncoder;

import org.firas.weixin.datatype.MpAuthorizeAccessToken;
import org.firas.weixin.datatype.MpUserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;

import org.firas.common.datatype.MutableInteger;
import org.firas.common.controller.RequestController;
import org.firas.common.response.JsonResponse;
import org.firas.common.response.JsonResponseSuccess;
import org.firas.common.response.JsonResponseFailUndefined;
import org.firas.common.response.input.JsonResponseInvalidInput;
import org.firas.common.response.notfound.JsonResponseNotFound;
import org.firas.common.request.InputValidation;
import org.firas.common.validator.StringValidator;
import org.firas.common.validator.IntegerValidator;
import org.firas.common.validator.UrlValidator;

import org.firas.weixin.datatype.AppNotFoundException;
import org.firas.weixin.datatype.MpSignResult;
import org.firas.weixin.service.MpService;
import org.firas.weixin.service.WeixinAppService;
import org.firas.weixin.model.WeixinApp;

@Controller
@RequestMapping("/weixin/mp")
@Slf4j
public class MpController extends RequestController {

    private MpService mpService;
    @Autowired
    public void setMpService(MpService mpService) {
        this.mpService = mpService;
    }

    private WeixinAppService appService;
    @Autowired
    public void setWeixinAppService(WeixinAppService appService) {
        this.appService = appService;
    }


    @RequestMapping(value="/sign/jsConfig", method=RequestMethod.GET)
    public @ResponseBody JsonResponse signForJsConfigAction(
        @RequestParam(required=false) String url,
        @RequestParam(name="app_name", required=false) String appName,
        @RequestParam(name="operator_ip", required=false) String operatorIp,
        @RequestParam(name="user_agent", required=false) String userAgent,
        @RequestParam(name="operator_id", required=false) String operatorId
    ) {
        MutableInteger operatorUserId = new MutableInteger();
        JsonResponseInvalidInput result = validateSignForJsConfigInput(
                url, appName, operatorId, operatorUserId);
        if (null != result) return result;

        try {
            WeixinApp app = appService.getWeixinAppByName(appName);
            if (null == app) return new JsonResponseNotFound(
                    "该名称的微信应用不存在",
                    "该名称的微信应用不存在", appName);
            MpSignResult signResult = mpService.sign(url, app);
            return new JsonResponseSuccess("", "微信公众平台JS接口签名成功",
                    signResult);
        } catch (RestClientException ex) {
            return this.dealWithInternalException(ex,
                    "访问微信公众平台接口失败");
        } catch (Exception ex) {
            return this.dealWithInternalException(ex,
                    "微信公众平台JS接口签名失败");
        }
    }

    private JsonResponseInvalidInput validateSignForJsConfigInput(String url,
            String name, String operatorId, MutableInteger operatorUserId) {
        Map<String, InputValidation> inputs = this.checkOperatorId(operatorId);
        inputs.put("url", new InputValidation(url,
                new UrlValidator("URL格式不正确")));
        inputs.put("app_name", new InputValidation(name,
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


    @RequestMapping(value="/downloadMedia", method=RequestMethod.GET)
    public ResponseEntity downloadMediaAction(
        @RequestParam(name="app_name", required=false) String appName,
        @RequestParam(name="media_id", required=false) String mediaId,
        @RequestParam(name="operator_ip", required=false) String operatorIp,
        @RequestParam(name="user_agent", required=false) String userAgent,
        @RequestParam(name="operator_id", required=false) String operatorId
    ) {
        MutableInteger operatorUserId = new MutableInteger();
        JsonResponseInvalidInput result = validateDownloadMediaInput(
                appName, mediaId, operatorId, operatorUserId);
        if (null != result) return new ResponseEntity<JsonResponse>(
                result, HttpStatus.OK);

        try {
            WeixinApp app = appService.getWeixinAppByName(appName);
            if (null == app) return new ResponseEntity<JsonResponse>(
                    new JsonResponseNotFound(
                        "该名称的微信应用不存在",
                        "该名称的微信应用不存在", appName),
                    HttpStatus.OK);

            return mpService.downloadMedia(app, mediaId);
        } catch (RestClientException ex) {
            return new ResponseEntity<JsonResponse>(
                    this.dealWithInternalException(ex,
                            "访问微信公众平台下载媒体文件接口失败"),
                    HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<JsonResponse>(
                    this.dealWithInternalException(ex,
                            "访问微信公众平台下载媒体文件接口失败"),
                    HttpStatus.OK);
        }
    }

    private JsonResponseInvalidInput validateDownloadMediaInput(
            String name, String mediaId,
            String operatorId, MutableInteger operatorUserId) {
        Map<String, InputValidation> inputs = this.checkOperatorId(operatorId);
        inputs.put("app_name", new InputValidation(name,
                new StringValidator(WeixinApp.NAME_MAX_LENGTH,
                        "微信应用名称最多" + WeixinApp.NAME_MAX_LENGTH + "个字符",
                        WeixinApp.NAME_MIN_LENGTH,
                        "微信应用名称最少" + WeixinApp.NAME_MIN_LENGTH + "个字符")));
        inputs.put("media_id", new InputValidation(mediaId,
                new StringValidator(null, null, 1, "媒体文件ID至少1个字符")));
        JsonResponseInvalidInput result = this.validateInput(inputs, true);
        if (null == result) {
            this.getOperatorUserId(inputs, operatorId, operatorUserId);
        }
        return result;
    }

    @RequestMapping(value="/authorize", method=RequestMethod.GET)
    public @ResponseBody JsonResponse getAuthorizeUriAction(
            @RequestParam(name="app_name", required=false) String appName,
            @RequestParam(name="redirect_uri", required=false) String uri,
            @RequestParam(name="state", required=false) String state,
            @RequestParam(name="operator_ip", required=false) String operatorIp,
            @RequestParam(name="user_agent", required=false) String userAgent,
            @RequestParam(name="operator_id", required=false) String operatorId
    ) {
        JsonResponseInvalidInput result = validateAuthorizeUriActionInput(uri, appName);
        if (null != result) return result;
        try {
            state = (null == state) ? "weixin" : URLEncoder.encode(state, "UTF-8");
            String encodeUri = URLEncoder.encode(uri,"UTF-8");
            String url = mpService.getAuthorizeUrl(appName, encodeUri, state);
            return new JsonResponseSuccess(
                    "获取微信授权重定向地址成功",
                    "获取微信授权重定向地址成功",
                    url
            );
        }catch(Exception ex) {
            this.dealWithInternalException(ex,
                    "获取微信授权重定向地址失败");
        }

        return new JsonResponseFailUndefined(
                "获取微信授权重定向地址失败",
                "获取微信授权重定向地址失败",
                uri
        );
    }

    private JsonResponseInvalidInput validateAuthorizeUriActionInput(
            String uri, String name) {
        Map<String, InputValidation> inputs = new HashMap<String, InputValidation>();
        inputs.put("app_name", new InputValidation(name,
                new StringValidator(WeixinApp.NAME_MAX_LENGTH,
                        "微信应用名称最多" + WeixinApp.NAME_MAX_LENGTH + "个字符",
                        WeixinApp.NAME_MIN_LENGTH,
                        "微信应用名称最少" + WeixinApp.NAME_MIN_LENGTH + "个字符")));
        inputs.put("redirect_uri", new InputValidation(uri,
                new UrlValidator("URI格式不正确")));
        JsonResponseInvalidInput result = this.validateInput(inputs, true);
        return result;
    }

    @RequestMapping(value="/userInfo", method=RequestMethod.GET)
    public @ResponseBody JsonResponse getAccessTokenUrlAction(
            @RequestParam(name="code", required=true) String code,
            @RequestParam(name="app_name", required=true) String appName,
            @RequestParam(name="operator_ip", required=false) String operatorIp,
            @RequestParam(name="user_agent", required=false) String userAgent,
            @RequestParam(name="operator_id", required=false) String operatorId
    ) {
        try {
            MpAuthorizeAccessToken token = mpService.getAuthorizeAccessToken(appName, code);
            String accessToken = token.getAccessToken();
            String openid = token.getOpenid();
            MpUserInfo userInfo = mpService.getWeiXinUserInfo(accessToken, openid, "zh_CN");
            log.debug("openId: "+ userInfo.getOpenid());
            return new JsonResponseSuccess(
                    "获取微信用户信息成功",
                    "获取微信用户信息成功",
                     userInfo.getMap()
            );
        }catch(Exception ex) {
            this.dealWithInternalException(ex,
                    "获取微信用户信息失败");
        }

        return new JsonResponseFailUndefined(
                "获取微信用户信息失败",
                "获取微信信息失败",
                appName+' '+ code
        );
    }

}
