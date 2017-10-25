package org.firas.sms.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.firas.common.model.IdModel;

@Entity
@Table(name = "t_sms")
@NoArgsConstructor
public class Sms extends IdModel {

    public Sms(Template template, String mobile, String content, String extra,
            String ip, String userAgent, Integer userId) {
        this.template = template;
        this.mobile = mobile;
        this.content = content;
        this.extra = extra;
        this.ip = ip;
        this.userAgent = userAgent;
        this.userId = userId;
    }

    public enum Provider {
        SELF((byte)0), ALIYUN((byte)1), YUN_TONG_XUN((byte)2),
        TESTIN((byte)3), YUNPIAN((byte)4);

        private byte provider;

        private Provider(byte provider) {
            this.provider = provider;
        }
    }

    @ManyToOne
    @JoinColumn(name = "template_id", nullable = false)
    @Getter @Setter private Template template;

    @Column(nullable = false, length = 32)
    @Getter @Setter private String mobile;

    @Column(nullable = false)
    @Getter @Setter private String content;

    @Column
    @Getter @Setter private String extra;

    @Column
    @Getter @Setter private String ip;

    @Column(name = "user_agent")
    @Getter @Setter private String userAgent;

    @Column(name = "user_id")
    @Getter @Setter private Integer userId;
}
