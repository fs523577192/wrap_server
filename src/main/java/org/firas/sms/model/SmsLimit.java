package org.firas.sms.model;

import java.util.Map;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import org.firas.common.helper.TrueValueHelper;
import org.firas.common.model.IdModel;

@Entity
@Table(name = "t_sms_limit")
@NoArgsConstructor
public class SmsLimit extends IdModel {

    public SmsLimit(Template template, Integer period,
            Integer maxCount, String message) {
        this.template = template;
        this.period = period;
        this.maxCount = maxCount;
        this.message = message;
    }

    @ManyToOne
    @JoinColumn(name = "template_id", nullable = false)
    @Getter @Setter private Template template;

    @Column(nullable = false)
    @Getter @Setter private Integer period;

    @Column(name = "max_count", nullable = false)
    @Getter @Setter private Integer maxCount;

    @Column(length = 32)
    @Getter @Setter private String message;

    public String descPeriod(String second, String minute, String hour) {
        int minutes = period / 60;
        int seconds = period % 60;
        int hours = minutes / 60;
        minutes = minutes % 60;
        StringBuilder result = new StringBuilder();
        if (hours > 0) {
            result.append(hours);
            result.append(null == hour ? "小时" : hour);
        }
        if (minutes > 0) {
            result.append(minutes);
            result.append(null == minute ? "分钟" : minute);
        }
        if (seconds > 0) {
            result.append(seconds);
            result.append(null == second ? "秒" : second);
        }
        return result.toString();
    }

    public HashMap<String, Object> toMap(Map<String, Object> options) {
        HashMap<String, Object> result = super.toMap(options);
        result.put("template_id", template.getId());
        result.put("period", period);
        result.put("max_count", maxCount);
        result.put("message", message);
        return result;
    }
}
