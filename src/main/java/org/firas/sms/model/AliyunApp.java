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

    public AliyunApp(App app, String endPoint,
            String appId, String appSecret, String url) {
        this.app = app;
        this.endPoint = endPoint;
        this.appId = appId;
        this.appSecret = appSecret;
        this.url = url;
    }

    public AliyunApp(String name, String endPoint,
            String appId, String appSecret, String url) {
        this(new App(Sms.Provider.ALIYUN, name), endPoint,
                appId, appSecret, url);
    }

    @Id
    @Column
    @Getter @Setter protected Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn(name = "id")
    @Getter private App app;
    public void setApp(App app) {
        this.setId(app.getId());
        this.app = app;
    }

    @Column(name = "end_point", nullable = false, length = 64)
    @Getter @Setter private String endPoint; // new interface: topic

    @Column(name = "app_id", nullable = false, length = 64)
    @Getter @Setter private String appId;

    @Column(name = "app_secret", nullable = false, length = 64)
    @Getter @Setter private String appSecret;

    @Column(nullable = false)
    @Getter @Setter private String url; // new interface: endpoint

    public HashMap<String, String> getMapForLog() {
        HashMap<String, String> map = this.getApp().getMapForLog();
        map.put("create_time", this.getCreateTimeInfo(null));
        map.put("update_time", this.getUpdateTimeInfo(null));
        map.put("end_point", this.getEndPoint());
        map.put("app_id", this.getAppId());
        map.put("app_secret", this.getAppSecret());
        map.put("url", this.getUrl());
        return map;
    }
}
