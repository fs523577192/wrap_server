package org.firas.weixin.model;

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
@Table(name = "t_weixin_app_log")
@DynamicUpdate
public class WeixinAppLog extends LogModel {
    @Transient
    private static final long serialVersionUID = 1L;

    public WeixinAppLog(Integer appId, Map<String, String> content, boolean isCreate,
            String operatorIp, String userAgent, Integer operatorId)
            throws JsonProcessingException {
        super(appId, new ObjectMapper().writeValueAsString(content),
                isCreate, operatorIp, userAgent, operatorId);
    }

}
