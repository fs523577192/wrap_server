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
import org.firas.common.response.occupied.JsonResponseOccupied;
import org.firas.common.request.InputValidation;
import org.firas.common.validator.StringValidator;
import org.firas.common.validator.IntegerValidator;
import org.firas.common.validator.ChineseMobileValidator;

import org.firas.sms.model.Template;
import org.firas.sms.model.SmsLimit;
import org.firas.sms.service.SmsLimitService;
import org.firas.sms.service.TemplateService;
import org.firas.sms.datatype.TemplateNotFoundException;
import org.firas.sms.datatype.LimitNotFoundException;
import org.firas.sms.datatype.PeriodOccupiedException;

@Controller
@RequestMapping("/sms/limit")
@Slf4j
public class SmsLimitController extends RequestController {

    private SmsLimitService smsLimitService;
    @Autowired
    public void setSmsLimitService(SmsLimitService smsLimitService) {
        this.smsLimitService = smsLimitService;
    }

    @RequestMapping(method=RequestMethod.POST)
    public @ResponseBody JsonResponse createAction(
        @RequestParam(value="template_id", required=false) String strTemplateId,
        @RequestParam(value="period", required=false) String strPeriod,
        @RequestParam(value="max_count", required=false) String strMaxCount,
        @RequestParam(value="message", required=false) String message,
        @RequestParam(value="operator_id", required=false) String strOperatorId,
        @RequestParam(value="operator_ip", required=false) String operatorIp,
        @RequestParam(value="user_agent", required=false) String userAgent
    ) {
        MutableInteger templateId = new MutableInteger();
        MutableInteger period = new MutableInteger();
        MutableInteger maxCount = new MutableInteger();
        MutableInteger operatorId = new MutableInteger();
        JsonResponseInvalidInput result = validateCreateInput(
                strTemplateId, templateId, strPeriod, period,
                strMaxCount, maxCount, message, strOperatorId, operatorId);
        if (null != result) return result;

        try {
            SmsLimit limit = smsLimitService.createSmsLimit(
                    templateId.getValue(), period.getValue(),
                    maxCount.getValue(), message, operatorIp,
                    userAgent, operatorId.getValue());
            Map<String, Object> options = new HashMap<String, Object>();
            options.put("id", true);
            return new JsonResponseSuccess("添加短信发送次数限制成功",
                    "添加短信发送次数限制成功", limit.toMap(options));
        } catch (TemplateNotFoundException ex) {
            return new JsonResponseNotFound(ex.getMessage(),
                    ex.getMessage(), templateId.getValue());
        } catch (PeriodOccupiedException ex) {
            HashMap<String, Integer> temp = new HashMap<String, Integer>();
            temp.put("template_id", templateId.getValue());
            temp.put("period", period.getValue());
            return new JsonResponseOccupied(ex.getMessage(),
                    ex.getMessage(), temp);
        } catch (Exception ex) {
            return this.dealWithInternalException(ex,
                    "添加短信发送次数限制失败");
        }
    }

    private JsonResponseInvalidInput validateCreateInput(
            String strTemplateId, MutableInteger templateId,
            String strPeriod, MutableInteger period,
            String strMaxCount, MutableInteger maxCount, String message,
            String strOperatorId, MutableInteger operatorId) {
        Map<String, InputValidation> inputs = this.checkOperatorId(
                strOperatorId);
        inputs.put("template_id", new InputValidation(strTemplateId,
                new IntegerValidator("短信模板的ID必须是一个正整数", 1, null,
                        "短信模板的ID必须是一个正整数",
                        "短信模板的ID必须是一个正整数")));
        inputs.put("period", new InputValidation(strPeriod,
                new IntegerValidator("时间间隔秒数必须是一个正整数", 1, null,
                        "时间间隔秒数必须是一个正整数",
                        "时间间隔秒数必须是一个正整数")));
        inputs.put("max_count", new InputValidation(strMaxCount,
                new IntegerValidator("最多发送次数必须是一个正整数", 1, null,
                        "最多发送次数必须是一个正整数",
                        "最多发送次数必须是一个正整数")));
        log.debug("length(" + message + ") == " + message.length());
        inputs.put("message", new InputValidation(message,
                new StringValidator(32, "超过限制的提示最多32个字符",
                        4, "超过限制的提示最少4个字符")));
        JsonResponseInvalidInput result = this.validateInput(inputs, true);
        if (null == result) {
            templateId.setValue(
                    (Integer)inputs.get("template_id").getNewValue());
            period.setValue(
                    (Integer)inputs.get("period").getNewValue());
            maxCount.setValue(
                    (Integer)inputs.get("max_count").getNewValue());
            this.getOperatorUserId(inputs, strOperatorId, operatorId);
        }
        return result;
    }

    @RequestMapping(method=RequestMethod.PATCH)
    public @ResponseBody JsonResponse updateAction(
        @RequestParam(value="id", required=false) String id,
        @RequestParam(value="period", required=false) String strPeriod,
        @RequestParam(value="max_count", required=false) String strMaxCount,
        @RequestParam(value="message", required=false) String message,
        @RequestParam(value="operator_id", required=false) String strOperatorId,
        @RequestParam(value="operator_ip", required=false) String operatorIp,
        @RequestParam(value="user_agent", required=false) String userAgent
    ) {
        MutableInteger limitId = new MutableInteger();
        MutableInteger period = new MutableInteger();
        MutableInteger maxCount = new MutableInteger();
        MutableInteger operatorId = new MutableInteger();
        JsonResponseInvalidInput result = validateCreateInput(
                id, limitId, strPeriod, period, strMaxCount, maxCount,
                message, strOperatorId, operatorId);
        if (null != result) return result;

        try {
            SmsLimit limit = smsLimitService.updateSmsLimit(
                    limitId.getValue(), period.getValue(),
                    maxCount.getValue(), message, operatorIp,
                    userAgent, operatorId.getValue());
            Map<String, Object> options = new HashMap<String, Object>();
            options.put("id", true);
            return new JsonResponseSuccess("修改短信发送次数限制成功",
                    "修改短信发送次数限制成功", limit.toMap(options));
        } catch (LimitNotFoundException ex) {
            return new JsonResponseNotFound(ex.getMessage(),
                    ex.getMessage(), limitId.getValue());
        } catch (PeriodOccupiedException ex) {
            HashMap<String, Integer> temp = new HashMap<String, Integer>();
            temp.put("period", period.getValue());
            return new JsonResponseOccupied(ex.getMessage(),
                    ex.getMessage(), temp);
        } catch (Exception ex) {
            return this.dealWithInternalException(ex,
                    "修改短信发送次数限制失败");
        }
    }

    private JsonResponseInvalidInput validateUpdateInput(
            String id, MutableInteger limitId,
            String strPeriod, MutableInteger period,
            String strMaxCount, MutableInteger maxCount, String message,
            String strOperatorId, MutableInteger operatorId) {
        Map<String, InputValidation> inputs = this.checkOperatorId(
                strOperatorId);
        inputs.put("id", new InputValidation(id,
                new IntegerValidator("短信发送次数限制的ID必须是一个正整数",
                        1, null, "短信发送次数限制的ID必须是一个正整数",
                        "短信发送次数限制的ID必须是一个正整数")));
        if (null != strPeriod) {
            inputs.put("period", new InputValidation(strPeriod,
                    new IntegerValidator("时间间隔秒数必须是一个正整数",
                            1, null, "时间间隔秒数必须是一个正整数",
                            "时间间隔秒数必须是一个正整数")));
        }
        if (null != strMaxCount) {
            inputs.put("max_count", new InputValidation(strMaxCount,
                    new IntegerValidator("最多发送次数必须是一个正整数",
                            1, null, "最多发送次数必须是一个正整数",
                            "最多发送次数必须是一个正整数")));
        }
        if (null != message) {
            inputs.put("message", new InputValidation(message,
                    new StringValidator(32, "超过限制的提示最多32个字符",
                            4, "超过限制的提示最少4个字符")));
        }
        JsonResponseInvalidInput result = this.validateInput(inputs, true);
        if (null == result) {
            if (null != strPeriod) {
                limitId.setValue(
                        (Integer)inputs.get("id").getNewValue());
            }
            if (null != strMaxCount) {
                period.setValue(
                        (Integer)inputs.get("period").getNewValue());
            }
            if (null != message) {
                maxCount.setValue(
                        (Integer)inputs.get("max_count").getNewValue());
            }
            this.getOperatorUserId(inputs, strOperatorId, operatorId);
        }
        return result;
    }

    @RequestMapping(value="/id", method=RequestMethod.GET)
    public @ResponseBody JsonResponse getByIdAction(
        @RequestParam(required=false) String id,
        @RequestParam(value="operator_id", required=false) String operatorId,
        @RequestParam(value="operator_ip", required=false) String operatorIp,
        @RequestParam(value="user_agent", required=false) String userAgent
    ) {
        MutableInteger limitId = new MutableInteger();
        MutableInteger operatorUserId = new MutableInteger();
        JsonResponseInvalidInput result = validateGetByIdInput(
                id, limitId, operatorId, operatorUserId);
        if (null != result) return result;

        SmsLimit smsLimit = smsLimitService.getLimitById(
                limitId.getValue());
        if (null == smsLimit) {
            return new JsonResponseNotFound(
                    "该ID的短信发送次数限制不存在",
                    "该ID的短信发送次数限制不存在", limitId.getValue());
        }

        Map<String, Object> options = new HashMap<String, Object>();
        options.put("id", true);
        return new JsonResponseSuccess("",
                    "按ID查询短信发送次数限制成功", smsLimit.toMap(options));
    }

    private JsonResponseInvalidInput validateGetByIdInput(
            String id, MutableInteger smsLimitId,
            String operatorId, MutableInteger operatorUserId) {
        HashMap<String, InputValidation> inputs =
                new HashMap<String, InputValidation>();
        inputs.put("id", new InputValidation(id,
                new IntegerValidator("短信发送次数限制ID必须是一个正整数",
                        1, null, "短信发送次数限制ID必须是一个正整数",
                        "短信发送次数限制ID必须是一个正整数")));
        if (null != operatorId) {
            inputs.put("operator_id", new InputValidation(operatorId,
                    new IntegerValidator("操作者用户ID必须是一个正整数",
                            1, null, "操作者用户ID必须是一个正整数",
                            "操作者用户ID必须是一个正整数")));
        }
        JsonResponseInvalidInput result = this.validateInput(inputs, true);
        if (null == result) {
            Object temp = inputs.get("id").getNewValue();
            smsLimitId.setValue((Integer)temp);
            if (null != operatorId) {
                operatorUserId.setValue(
                        (Integer)inputs.get("operator_id").getNewValue());
            }
        }
        return result;
    }
}
