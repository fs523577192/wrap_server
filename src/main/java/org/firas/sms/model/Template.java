package org.firas.sms.model;

import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.firas.common.model.IdModel;

@Entity
@Table(name = "t_template")
@NoArgsConstructor
public class Template extends IdModel {

    public Template(App app, String name) {
        this.app = app;
        this.provider = app.getProvider();
        this.name = name;
    }

    @Column
    @Enumerated(EnumType.ORDINAL)
    @Getter @Setter private Sms.Provider provider;
    
    @Column(nullable = false, length = 32)
    @Getter @Setter private String name;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "app_id", nullable = false)
    @Getter @Setter private App app;
    
    public HashMap<String, String> getMapForLog() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("id", this.getId().toString());
        map.put("create_time", this.getCreateTimeInfo(null));
        map.put("update_time", this.getUpdateTimeInfo(null));
        map.put("app_id", this.getApp().getId().toString());
        map.put("name", this.getName());
        map.put("provider", this.getProvider().toString());
        return map;
    }
}
