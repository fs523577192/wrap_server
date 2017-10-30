package org.firas.jiadian.controller;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
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
import org.firas.jiadian.service.OrderService;
import org.firas.jiadian.entity.Order;

@Controller
@RequestMapping("/jiadian/weixin-mp")
@Slf4j
public class MpController extends RequestController {

    private MpService mpService;
    @Autowired
    public void setMpService(MpService mpService) {
        this.mpService = mpService;
    }

    private OrderService orderService;
    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
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
        if (mobilePattern.matcher(input.getContent()).matches()) {
            return reply(input,
                    findByMobile(input.getContent().substring(1)));
        } else if (schoolIdCodePattern.matcher(
                input.getContent()).matches()) {
            return reply(input,
                    findBySchoolIdCode(input.getContent().substring(1)));
        }
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

    private static final Pattern mobilePattern = Pattern.compile(
            "^查1\\d{10}$");
    private static final Pattern schoolIdCodePattern = Pattern.compile(
            "^查\\d{6,10}$");
    private String findByMobile(String mobile) {
        List<Order> list = orderService.listByMobile(mobile);
        if (list.isEmpty()) {
            return "找不到该手机号的维修记录";
        }
        StringBuilder buffer = new StringBuilder("该手机号的维修记录：");
        for (Order item : list) {
            buffer.append("\n物品：");
            buffer.append(padRight(truncateString(item.getName(), 8), 12));
            buffer.append(item.getStatusInfo());
        }
        return buffer.toString();
    }

    private String findBySchoolIdCode(String schoolIdCode) {
        List<Order> list = orderService.listBySchoolIdCode(schoolIdCode);
        if (list.isEmpty()) {
            return "找不到该学号的维修记录";
        }
        StringBuilder buffer = new StringBuilder("该学号的维修记录：");
        for (Order item : list) {
            buffer.append("\n物品：");
            buffer.append(padRight(truncateString(item.getName(), 8), 12));
            buffer.append(item.getStatusInfo());
        }
        return buffer.toString();
    }

    private static String padRight(String str, int length) {
        StringBuilder buffer = new StringBuilder(str);
        for (int i = length - buffer.length(); i > 0; i -= 1) {
            buffer.append(' ');
        }
        return buffer.toString();
    }

    private static String truncateString(String str, int length) {
        if (str.length() < length) {
            return str;
        }
        return str.substring(0, length) + "...";
    }

    private static ResponseEntity<String> reply(
            ReceiveMessageInput input, String content) {
        StringBuilder buffer = new StringBuilder("<xml><ToUserName><![CDATA[");
        buffer.append(input.getFromUserName());
        buffer.append("]]></ToUserName><FromUserName><![CDATA[");
        buffer.append(input.getToUserName());
        buffer.append("]]></FromUserName><CreateTime>");
        buffer.append(new Date().getTime());
        buffer.append("</CreateTime><MsgType><![CDATA[");
        buffer.append("text]]></MsgType><Content><![CDATA[");
        buffer.append(content);
        buffer.append("]]></Content></xml>");
        return new ResponseEntity<String>(buffer.toString(),
                HttpStatus.OK);
    }
}
