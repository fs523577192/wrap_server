package org.firas.sms.model;

import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.firas.common.model.StatusModel;

@Entity
@Table(name = "t_aliyun_app")
@NoArgsConstructor
public class AliyunApp extends StatusModel {
    @Transient
    private static final long serialVersionUID = 1L;

    public AliyunApp(SmsApp app, String appId, String appSecret,
            String endPoint, String topic) {
        this.app = app;
        this.appId = appId;
        this.appSecret = appSecret;
        this.endPoint = endPoint;
        this.topic = topic;
    }

    public AliyunApp(String name, String appId, String appSecret,
            String endPoint, String topic) {
        this(new SmsApp(Sms.Provider.ALIYUN, name), endPoint,
                appId, appSecret, topic);
    }

    @Id
    @Column
    @Getter @Setter protected Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn(name = "id")
    @Getter private SmsApp app;
    public void setApp(SmsApp app) {
        this.setId(app.getId());
        this.app = app;
    }

    @Column(name = "app_id", nullable = false, length = 64)
    @Getter @Setter private String appId;

    @Column(name = "app_secret", nullable = false, length = 64)
    @Getter @Setter private String appSecret;

    @Column(name = "end_point", nullable = false)
    @Getter @Setter private String endPoint;

    @Column(nullable = false, length = 64)
    @Getter @Setter private String topic;

    public HashMap<String, String> getMapForLog() {
        HashMap<String, String> map = this.getApp().getMapForLog();
        map.put("create_time", this.getCreateTimeInfo(null));
        map.put("update_time", this.getUpdateTimeInfo(null));
        map.put("app_id", this.getAppId());
        map.put("app_secret", this.getAppSecret());
        map.put("end_point", this.getEndPoint());
        map.put("topic", this.getTopic());
        return map;
    }
}
