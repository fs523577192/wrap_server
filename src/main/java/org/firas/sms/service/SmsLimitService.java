package org.firas.sms.service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.firas.sms.dao.SmsLimitDao;
import org.firas.sms.dao.SmsLimitLogDao;
import org.firas.sms.model.SmsLimit;
import org.firas.sms.model.SmsLimitLog;
import org.firas.sms.model.Template;
import org.firas.sms.datatype.TemplateNotFoundException;
import org.firas.sms.datatype.LimitNotFoundException;
import org.firas.sms.datatype.PeriodOccupiedException;

@Service
public class SmsLimitService {

    private TemplateService templateService;
    @Autowired
    public void setTemplateService(TemplateService templateService) {
        this.templateService = templateService;
    }

    private SmsLimitDao smsLimitDao;
    @Autowired
    public void setSmsLimitDao(SmsLimitDao smsLimitDao) {
        this.smsLimitDao = smsLimitDao;
    }

    private SmsLimitLogDao smsLimitLogDao;
    @Autowired
    public void setSmsLimitLogDao(SmsLimitLogDao smsLimitLogDao) {
        this.smsLimitLogDao = smsLimitLogDao;
    }

    public SmsLimit getLimitById(Integer limitId) {
        return smsLimitDao.findOne(limitId);
    }

    public SmsLimit getLimitByTemplateAndPeriod(Template template,
            Integer period) {
        return smsLimitDao.findFirstByTemplateAndPeriod(template, period);
    }

    public List<SmsLimit> findLimitByTemplate(Template template) {
        return smsLimitDao.findByTemplateOrderByPeriodDesc(template);
    }

    @Transactional
    public SmsLimit createSmsLimit(Integer templateId, Integer period,
            Integer maxCount, String message, String operatorIp,
            String userAgent, Integer operatorId
    ) throws JsonProcessingException, TemplateNotFoundException,
    PeriodOccupiedException {
        Template template = templateService.getTemplateById(templateId);
        if (null == template) throw new TemplateNotFoundException(
                "ID为" + templateId + "的短信模板不存在");

        checkConflict(template, period, null);

        SmsLimit smsLimit = smsLimitDao.save(new SmsLimit(template,
                period, maxCount, message));
        Map<String, Object> change = new HashMap<String, Object>();
        change.put("template_id", templateId);
        change.put("period", period);
        change.put("max_count", maxCount);
        change.put("message", message);
        SmsLimitLog log = smsLimitLogDao.save(new SmsLimitLog(
                smsLimit.getId(), change, true, operatorIp,
                userAgent, operatorId));
        return smsLimit;
    }

    @Transactional
    public SmsLimit updateSmsLimit(Integer limitId, Integer period,
            Integer maxCount, String message, String operatorIp,
            String userAgent, Integer operatorId
    ) throws JsonProcessingException, LimitNotFoundException,
    PeriodOccupiedException {
        SmsLimit limit = getLimitById(limitId);
        if (null == limit) throw new LimitNotFoundException(
                "ID为" + limitId + "的短信发送限制不存在");

        checkConflict(limit.getTemplate(), period, limitId);

        Map<String, Object> change = new HashMap<String, Object>();
        if (null != period && limit.getPeriod() != period) {
            limit.setPeriod(period);
            change.put("period", period);
        }
        if (null != maxCount && limit.getMaxCount() != maxCount) {
            limit.setMaxCount(maxCount);
            change.put("max_count", maxCount);
        }
        if (null != message && !limit.getMessage().equals(message)) {
            limit.setMessage(message);
            change.put("message", message);
        }
        limit = smsLimitDao.save(limit);
        SmsLimitLog log = smsLimitLogDao.save(new SmsLimitLog(
                limitId, change, false, operatorIp,
                userAgent, operatorId));
        return limit;
    }

    public void checkConflict(Template template, Integer period,
            Integer limitId) {
        SmsLimit limit = getLimitByTemplateAndPeriod(template, period);
        if (null != limit && limit.getId() != limitId) {
            throw new PeriodOccupiedException(template.getId(), period);
        }
    }
}
