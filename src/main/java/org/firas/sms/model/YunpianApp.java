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
@Table(name = "t_yunpian_app")
@NoArgsConstructor
public class YunpianApp extends StatusModel {
    @Transient
    private static final long serialVersionUID = 1L;

    public YunpianApp(SmsApp app, String apiKey) {
        this.app = app;
        this.apiKey = apiKey;
    }

    public YunpianApp(String name, String apiKey) {
        this(new SmsApp(Sms.Provider.ALIYUN, name), apiKey);
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

    @Column(name = "api_key", nullable = false, length = 64)
    @Getter @Setter private String apiKey;

}
