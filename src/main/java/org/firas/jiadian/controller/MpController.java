package org.firas.jiadian.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
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
import org.firas.weixin.input.ReceiveMessageInput;
import org.firas.weixin.service.MpService;
import org.firas.weixin.service.WeixinAppService;
import org.firas.weixin.model.WeixinApp;

@Controller
@RequestMapping("/jiadian/weixin-mp")
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


    @RequestMapping(value="/receiver", method=RequestMethod.GET)
    public ResponseEntity<String> echoAction(
        @RequestParam(required=false) String echostr,
        @RequestParam(required=false) String nonce,
        @RequestParam(required=false) String timestamp,
        @RequestParam(required=false) String signature,
        HttpServletRequest request
    ) {
        log.debug(this.getRequestIp(request));
        String token = "org0firas1weixin2mp3token";
        String AESKey = "kT0JJdEjxT6YEi63Go5DZx5biOrPQdM1rCSp8KtraEG";
        if (MpService.checkEcho(nonce, token, timestamp, signature) &&
                null != echostr) {
            log.debug(echostr);
            return new ResponseEntity<String>(echostr, HttpStatus.OK);
        }
        log.debug("fail");
        return new ResponseEntity<String>("fail", HttpStatus.OK);
    }

    @RequestMapping(value="/receiver", method=RequestMethod.POST)
    public ResponseEntity<String> receiveMessageAction(
        @RequestBody ReceiveMessageInput input,
        HttpServletRequest request
    ) {
        log.debug(this.getRequestIp(request));
        log.debug(String.valueOf(input.isTextMessage()));
        log.debug(String.valueOf(input.getContent()));
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

}
