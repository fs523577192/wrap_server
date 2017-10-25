package org.firas.weixin.dao;

import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.firas.common.dao.DefaultRedisDao;

@Repository
public class JsapiTicketDao extends DefaultRedisDao<String, String> {

    public static final String PREFIX = "weixin.mp.jsapi_ticket.";

    @Autowired
    public JsapiTicketDao(RedisConnectionFactory factory) {
        super(factory);
    }

    public void cache(Integer weixinAppId, String jsapiTicket, int seconds) {
        this.setByKey(PREFIX + weixinAppId, jsapiTicket, (long)seconds, TimeUnit.SECONDS);
    }

    public String get(Integer weixinAppId) {
        return this.getByKey(PREFIX + weixinAppId);
    }
}
