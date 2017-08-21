package org.firas.common.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@MappedSuperclass
public abstract class LogModel extends IdModel {

    public LogModel(Integer modelId, String content, boolean isCreate,
            String operatorIp, String userAgent, Integer operatorId) {
        this.modelId = modelId;
        this.content = content;
        this.isCreate = isCreate;
        this.operatorIp = operatorIp;
        this.userAgent = userAgent;
        this.operatorId = operatorId;
    }

    @Column(name = "model_id", nullable = false)
    @Getter @Setter private Integer modelId;

    @Column(name = "is_create", nullable = false)
    @Getter @Setter private boolean isCreate = false;

    @Column(name = "operator_ip")
    @Getter @Setter private String operatorIp;

    @Column(name = "user_agent")
    @Getter @Setter private String userAgent;

    @Column(name = "operator_id")
    @Getter @Setter private Integer operatorId;

    @Column(nullable = false)
    @Getter @Setter private String content;

}
