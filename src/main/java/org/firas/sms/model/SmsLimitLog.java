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
@Table(name = "t_sms_limit_log")
@DynamicUpdate
public class SmsLimitLog extends LogModel {
    @Transient
    private static final long serialVersionUID = 1L;

    public SmsLimitLog(Integer limitId, Map<String, Object> content,
            boolean isCreate, String operatorIp, String userAgent,
            Integer operatorId) throws JsonProcessingException {
        super(limitId, new ObjectMapper().writeValueAsString(content),
                isCreate, operatorIp, userAgent, operatorId);
    }

}
