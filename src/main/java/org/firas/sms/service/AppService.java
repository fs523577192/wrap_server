package org.firas.sms.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.firas.sms.dao.AppDao;
import org.firas.sms.dao.AliyunAppDao;
import org.firas.sms.dao.AppLogDao;
import org.firas.sms.model.App;
import org.firas.sms.model.AliyunApp;
import org.firas.sms.model.AppLog;

@Service
public class AppService {

    private AppDao appDao;
    @Autowired
    public void setAppDao(AppDao appDao) {
        this.appDao = appDao;
    }

    private AliyunAppDao aliyunAppDao;
    @Autowired
    public void setAliyunAppDao(AliyunAppDao aliyunAppDao) {
        this.aliyunAppDao = aliyunAppDao;
    }

    private AppLogDao appLogDao;
    @Autowired
    public void setAppLogDao(AppLogDao appLogDao) {
        this.appLogDao = appLogDao;
    }

    public App getAppById(Integer appId) {
        return appDao.findOne(appId);
    }

    public AliyunApp getAliyunAppById(Integer appId) {
        return aliyunAppDao.findFirstById(appId);
    }

    public App getAppByName(String name) {
        return appDao.findFirstByName(name);
    }

    @Transactional
    public AliyunApp createAliyunApp(AliyunApp aliyunApp,
            String operatorIp, String userAgent, Integer operatorId)
            throws JsonProcessingException {
        aliyunApp.setApp( appDao.save(aliyunApp.getApp()) );
        aliyunApp = aliyunAppDao.save(aliyunApp);
        AppLog appLog = new AppLog(aliyunApp.getId(), aliyunApp.getMapForLog(),
                true, operatorIp, userAgent, operatorId);
        appLog = appLogDao.save(appLog);
        return aliyunApp;
    }
}
