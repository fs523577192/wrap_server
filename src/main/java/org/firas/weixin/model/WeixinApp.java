package org.firas.weixin.model;

import java.util.Map;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.firas.common.model.IdModel;

@Entity
@Table(name = "t_weixin_app")
@NoArgsConstructor
public class WeixinApp extends IdModel {

    public WeixinApp(String name, String appId, String appSecret) {
        this.name= name;
        this.appId= appId;
        this.appSecret= appSecret;
    }

    public static final int NAME_MAX_LENGTH = 32;
    public static final int NAME_MIN_LENGTH = 2;
    @Column(nullable = false, length = NAME_MAX_LENGTH)
    @Getter @Setter private String name;
    
    public static final int APP_ID_MAX_LENGTH = 64;
    public static final int APP_ID_MIN_LENGTH = 4;
    @Column(name = "app_id", nullable = false, length = APP_ID_MAX_LENGTH)
    @Getter @Setter private String appId;

    public static final int SECRET_MAX_LENGTH = 64;
    public static final int SECRET_MIN_LENGTH = 4;
    @Column(name = "app_secret", nullable = false, length = SECRET_MAX_LENGTH)
    @Getter @Setter private String appSecret;

    public HashMap<String, String> getMapForLog() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("id", this.getId().toString());
        map.put("create_time", this.getCreateTimeInfo(null));
        map.put("update_time", this.getUpdateTimeInfo(null));
        map.put("name", this.getName());
        map.put("appId", this.getAppId());
        map.put("appSecret", this.getAppSecret());
        return map;
    }

    public HashMap<String, Object> toMap(Map<String, Object> options) {
        HashMap<String, Object> result = super.toMap(options);
        result.put("name", this.getName());
        result.put("appId", this.getAppId());
        result.put("appSecret", this.getAppSecret());
        return result;
    }

}
