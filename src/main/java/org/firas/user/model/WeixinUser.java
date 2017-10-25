package org.firas.user.model;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.MappedSuperclass;
import javax.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.firas.common.helper.TrueValueHelper;

@MappedSuperclass
@NoArgsConstructor
public class WeixinUser extends UserBase {

    public WeixinUser(String userName, String unionId, String openId) {
        super(userName);
        this.unionId = unionId;
        this.openId = openId;
    }

    @Override
    public HashMap<String, Object> toMap(Map<String, Object> options) {
        HashMap<String, Object> result = super.toMap(options);
        if (TrueValueHelper.isTrue(options, "weixin")) {
            result.put("union_id", this.getUnionId());
            result.put("open_id", this.getOpenId());
        }
        return result;
    }

    @Column(name = "union_id", nullable = false)
    @Getter @Setter private String unionId;

    @Column(name = "open_id", nullable = false)
    @Getter @Setter private String openId;
}
