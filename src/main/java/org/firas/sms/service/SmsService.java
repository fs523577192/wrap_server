package org.firas.sms.service;

import java.util.List;
import java.util.Date;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;

import org.firas.sms.dao.SmsDao;
import org.firas.sms.model.Sms;
import org.firas.sms.model.SmsLimit;
import org.firas.sms.model.Template;
import org.firas.sms.datatype.SendFrequentlyException;

@Service
@Slf4j
public class SmsService {

    private SmsDao smsDao;
    @Autowired
    public void setSmsDao(SmsDao smsDao) {
        this.smsDao = smsDao;
    }

    private SmsLimitService smsLimitService;
    @Autowired
    public void setSmsLimitService(SmsLimitService smsLimitService) {
        this.smsLimitService = smsLimitService;
    }

    public Sms getSmsById(Integer smsId) {
        log.debug("smsId == " + smsId);
        return smsDao.findOne(smsId);
    }

    public List<Sms> findByMobileAfter(Template template,
            String mobile, Date time) {
        return smsDao.findByTemplateAndMobileAndCreateTimeGreaterThanOrderByIdDesc(
                template, mobile, time);
    }

    public Sms createSms(Template template, String mobile, String content,
            String extra, String ip, String userAgent, Integer userId) {
        Sms sms = new Sms(template, mobile, content, extra,
                ip, userAgent, userId);
        return smsDao.save(sms);
    }

    public SmsLimit checkPrevious(Template template, String mobile) {
        List<SmsLimit> limits = smsLimitService.findLimitByTemplate(template);
        if (limits.size() < 1) return null;

        Integer maxPeriod = limits.get(0).getPeriod();
        long now = new Date().getTime();
        Date before = new Date(now - maxPeriod);
        List<Sms> previous = findByMobileAfter(template, mobile, before);

        int maxJ = previous.size();
        for (int i = limits.size(); i-- > 0; ) {
            SmsLimit limit = limits.get(i);
            for (int j = 0; j < maxJ; ++j) {
                Sms sms = previous.get(j);
                long interval = now - sms.getCreateTime().getTime();
                log.debug("j == " + j + ", interval == " + interval +
                        ", period == " + limit.getPeriod());
                if (interval > limit.getPeriod()) {
                    maxJ = j;
                    break;
                }
                log.debug("maxCount == " + limit.getMaxCount());
                if (maxJ >= limit.getMaxCount()) return limit;
            }
        }
        return null;
    }
}
