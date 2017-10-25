package org.firas.sms.service;

import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.firas.sms.dao.TemplateDao;
import org.firas.sms.dao.AliyunTemplateDao;
import org.firas.sms.dao.TemplateLogDao;
import org.firas.sms.model.Template;
import org.firas.sms.model.AliyunTemplate;
import org.firas.sms.model.TemplateLog;

@Service
public class TemplateService {

    private TemplateDao templateDao;
    @Autowired
    public void setTemplateDao(TemplateDao templateDao) {
        this.templateDao = templateDao;
    }

    private AliyunTemplateDao aliyunTemplateDao;
    @Autowired
    public void setAliyunTemplateDao(AliyunTemplateDao aliyunTemplateDao) {
        this.aliyunTemplateDao = aliyunTemplateDao;
    }

    private TemplateLogDao templateLogDao;
    @Autowired
    public void setTemplateLogDao(TemplateLogDao templateLogDao) {
        this.templateLogDao = templateLogDao;
    }

    public Template getTemplateById(Integer templateId) {
        return templateDao.findOne(templateId);
    }

    public Template getTemplateByAppIdAndName(Integer appId, String name) {
        return templateDao.findFirstByAppIdAndName(appId, name);
    }

    public Template getTemplateByAppNameAndName(String appName, String name) {
        return templateDao.findFirstByApp_NameAndName(appName, name);
    }

    public AliyunTemplate getAliyunTemplateByTemplateId(Integer templateId) {
        return aliyunTemplateDao.findFirstByTemplateId(templateId);
    }

    @Transactional
    public AliyunTemplate createAliyunTemplate(AliyunTemplate aliyunTemplate,
            String operatorIp, String userAgent, Integer operatorId)
            throws JsonProcessingException {
        aliyunTemplate.setTemplate( templateDao.save(
                aliyunTemplate.getTemplate()) );
        aliyunTemplate = aliyunTemplateDao.save(aliyunTemplate);
        TemplateLog templateLog = new TemplateLog(
                aliyunTemplate.getTemplateId(), aliyunTemplate.getMapForLog(),
                true, operatorIp, userAgent, operatorId);
        templateLog = templateLogDao.save(templateLog);
        return aliyunTemplate;
    }

    @Transactional
    public AliyunTemplate updateAliyunTemplate(AliyunTemplate aliyunTemplate,
            Template template, Map<String, String> change, String operatorIp,
            String userAgent, Integer operatorId)
            throws JsonProcessingException {
        aliyunTemplate.setTemplate(templateDao.save(template));
        aliyunTemplate = aliyunTemplateDao.save(aliyunTemplate);
        TemplateLog templateLog = new TemplateLog(
                aliyunTemplate.getTemplateId(), change,
                false, operatorIp, userAgent, operatorId);
        templateLog = templateLogDao.save(templateLog);
        return aliyunTemplate;
    }

}
