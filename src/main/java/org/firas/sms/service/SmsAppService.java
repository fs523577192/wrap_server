package org.firas.sms.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.firas.sms.dao.SmsAppDao;
import org.firas.sms.dao.AliyunAppDao;
import org.firas.sms.dao.SmsAppLogDao;
import org.firas.sms.model.SmsApp;
import org.firas.sms.model.AliyunApp;
import org.firas.sms.model.SmsAppLog;

@Service
public class SmsAppService {

    private SmsAppDao appDao;
    @Autowired
    public void setAppDao(SmsAppDao appDao) {
        this.appDao = appDao;
    }

    private AliyunAppDao aliyunAppDao;
    @Autowired
    public void setAliyunAppDao(AliyunAppDao aliyunAppDao) {
        this.aliyunAppDao = aliyunAppDao;
    }

    private SmsAppLogDao appLogDao;
    @Autowired
    public void setAppLogDao(SmsAppLogDao appLogDao) {
        this.appLogDao = appLogDao;
    }

    public SmsApp getAppById(Integer appId) {
        return appDao.findOne(appId);
    }

    public AliyunApp getAliyunAppById(Integer appId) {
        return aliyunAppDao.findFirstById(appId);
    }

    public SmsApp getAppByName(String name) {
        return appDao.findFirstByName(name);
    }

    @Transactional
    public AliyunApp createAliyunApp(AliyunApp aliyunApp,
            String operatorIp, String userAgent, Integer operatorId)
            throws JsonProcessingException {
        aliyunApp.setApp( appDao.save(aliyunApp.getApp()) );
        aliyunApp = aliyunAppDao.save(aliyunApp);
        SmsAppLog appLog = new SmsAppLog(aliyunApp.getId(), aliyunApp.getMapForLog(),
                true, operatorIp, userAgent, operatorId);
        appLog = appLogDao.save(appLog);
        return aliyunApp;
    }
}
