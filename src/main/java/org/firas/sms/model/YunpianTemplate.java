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
@Table(name = "t_yunpian_template")
@NoArgsConstructor
public class YunpianTemplate extends StatusModel {
    @Transient
    private static final long serialVersionUID = 1L;

    public YunpianTemplate(Template template, String content) {
        this.template = template;
        this.content = content;
    }

    public YunpianTemplate(App app, String name, String content) {
        this(new Template(app, name), content);
    }

    @Id
    @Column(name = "template_id")
    @Getter protected Integer templateId;
    public YunpianTemplate setTemplateId(Integer templateId) {
        if (this.templateId != templateId) {
            this.templateId = templateId;
            this.template = null;
        }
        return this;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn(name = "template_id")
    @Getter private Template template;
    public YunpianTemplate setTemplate(Template template) {
        this.templateId = template.getId();
        this.template = template;
        return this;
    }

    @Column(nullable = false, length = 64)
    @Getter @Setter private String content;

    public HashMap<String, String> getMapForLog() {
        HashMap<String, String> map = this.getTemplate().getMapForLog();
        map.put("create_time", this.getCreateTimeInfo(null));
        map.put("update_time", this.getUpdateTimeInfo(null));
        map.put("content", this.getContent());
        return map;
    }
}
