package org.firas.weixin.service;

import java.util.Map;
import java.util.HashMap;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.firas.weixin.datatype.AppNameOccupiedException;
import org.firas.weixin.datatype.AppNotFoundException;
import org.firas.weixin.dao.WeixinAppDao;
import org.firas.weixin.dao.WeixinAppLogDao;
import org.firas.weixin.model.WeixinApp;
import org.firas.weixin.model.WeixinAppLog;

@Service
public class WeixinAppService {

    private WeixinAppDao appDao;
    @Autowired
    public void setWeixinAppDao(WeixinAppDao appDao) {
        this.appDao = appDao;
    }

    private WeixinAppLogDao appLogDao;
    @Autowired
    public void setWeixinAppLogDao(WeixinAppLogDao appLogDao) {
        this.appLogDao = appLogDao;
    }

    public WeixinApp getWeixinAppById(Integer appId) {
        return appDao.findFirstById(appId);
    }

    public WeixinApp getWeixinAppByName(String name) {
        return appDao.findFirstByName(name);
    }

    @Transactional
    public WeixinApp createWeixinApp(
            String name, String appId, String appSecret,
            String operatorIp, String userAgent, Integer operatorId
    ) throws JsonProcessingException, AppNameOccupiedException {
        WeixinApp app = getWeixinAppByName(name);
        if (null != app) throw new AppNameOccupiedException(app);

        Map<String, String> change = new HashMap<String, String>();
        change.put("name", name);
        change.put("app_id", appId);
        change.put("app_secret", appSecret);
        
        app = appDao.save(new WeixinApp(name, appId, appSecret));

        WeixinAppLog appLog = new WeixinAppLog(app.getId(), change,
                true, operatorIp, userAgent, operatorId);
        appLog = appLogDao.save(appLog);
        return app;
    }

    @Transactional
    public WeixinApp updateWeixinApp(Integer id,
            String name, String appId, String appSecret,
            String operatorIp, String userAgent, Integer operatorId)
            throws JsonProcessingException, AppNameOccupiedException,
            AppNotFoundException {
        WeixinApp app = getWeixinAppById(id);
        if (null == app) throw new AppNotFoundException(
                "该ID的微信应用不存在");

        Map<String, String> change = new HashMap<String, String>();
        if (null != name && !app.getName().equals(name)) {
            WeixinApp temp = getWeixinAppByName(name);
            if (null != temp) throw new AppNameOccupiedException(temp);
            change.put("name", name);
            app.setName(name);
        }
        if (null != appId && !app.getAppId().equals(appId)) {
            change.put("app_id", appId);
            app.setAppId(appId);
        }
        if (null != appSecret && !app.getAppSecret().equals(appSecret)) {
            change.put("app_secret", appSecret);
            app.setAppSecret(appSecret);
        }
        app = appDao.save(app);
        WeixinAppLog appLog = new WeixinAppLog(app.getId(), change,
                false, operatorIp, userAgent, operatorId);
        appLog = appLogDao.save(appLog);
        return app;
    }
}
