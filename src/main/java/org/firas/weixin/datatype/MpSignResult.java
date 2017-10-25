package org.firas.weixin.datatype;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MpSignResult implements Serializable {

    @Getter @Setter private String appId;
    @Getter @Setter private String nonceStr;
    @Getter @Setter private long timestamp;
    @Getter @Setter private String signature;
}
