package org.firas.sms.model;

import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
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
@Table(name = "t_aliyun_template")
@NoArgsConstructor
public class AliyunTemplate extends StatusModel {
    @Transient
    private static final long serialVersionUID = 1L;

    public AliyunTemplate(Template template, String signName, String code) {
        this.template = template;
        this.signName = signName;
        this.code = code;
    }

    public AliyunTemplate(SmsApp app, String name, String signName, String code) {
        this(new Template(app, name), signName, code);
    }

    @Id
    @Column(name = "template_id")
    @Getter protected Integer templateId;
    public AliyunTemplate setTemplateId(Integer templateId) {
        if (this.templateId != templateId) {
            this.templateId = templateId;
            this.template = null;
        }
        return this;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn(name = "template_id")
    @Getter private Template template;
    public AliyunTemplate setTemplate(Template template) {
        this.templateId = template.getId();
        this.template = template;
        return this;
    }

    @Column(name = "sign_name", nullable = false, length = 32)
    @Getter @Setter private String signName;

    @Column(nullable = false, length = 32)
    @Getter @Setter private String code;

    public HashMap<String, String> getMapForLog() {
        HashMap<String, String> map = this.getTemplate().getMapForLog();
        map.put("create_time", this.getCreateTimeInfo(null));
        map.put("update_time", this.getUpdateTimeInfo(null));
        map.put("sign_name", this.getSignName());
        map.put("code", this.getCode());
        return map;
    }
}
