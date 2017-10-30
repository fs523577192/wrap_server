package org.firas.sms.model;

import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.firas.common.model.IdModel;

@Entity
@Table(name = "t_sms_app")
@NoArgsConstructor
public class SmsApp extends IdModel {

    public SmsApp(Sms.Provider provider, String name) {
        this.provider = provider;
        this.name = name;
    }

    @Column
    @Enumerated(EnumType.ORDINAL)
    @Getter @Setter private Sms.Provider provider;

    @Column(nullable = false, length = 32)
    @Getter @Setter private String name;
    
    public HashMap<String, String> getMapForLog() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("id", this.getId().toString());
        map.put("create_time", this.getCreateTimeInfo(null));
        map.put("update_time", this.getUpdateTimeInfo(null));
        map.put("name", this.getName());
        map.put("provider", this.getProvider().toString());
        return map;
    }
}
