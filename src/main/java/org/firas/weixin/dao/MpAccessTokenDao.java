package org.firas.weixin.dao;

import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.firas.common.dao.DefaultRedisDao;

@Repository
public class MpAccessTokenDao extends DefaultRedisDao<String, String> {

    public static final String PREFIX = "weixin.mp.access_token.";

    @Autowired
    public MpAccessTokenDao(RedisConnectionFactory factory) {
        super(factory);
    }

    public void cache(Integer weixinAppId, String accessToken, int seconds) {
        this.setByKey(PREFIX + weixinAppId, accessToken, (long)seconds, TimeUnit.SECONDS);
    }

    public String get(Integer weixinAppId) {
        return this.getByKey(PREFIX + weixinAppId);
    }
}
