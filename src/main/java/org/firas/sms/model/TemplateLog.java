package org.firas.sms.model;

import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.DynamicUpdate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;
import org.firas.common.model.LogModel;

@Entity
@Table(name = "t_template_log")
@DynamicUpdate
public class TemplateLog extends LogModel {
    @Transient
    private static final long serialVersionUID = 1L;

    public TemplateLog(Integer templateId, Map<String, String> content,
            boolean isCreate, String operatorIp, String userAgent,
            Integer operatorId) throws JsonProcessingException {
        super(templateId, new ObjectMapper().writeValueAsString(content),
                isCreate, operatorIp, userAgent, operatorId);
    }

}
