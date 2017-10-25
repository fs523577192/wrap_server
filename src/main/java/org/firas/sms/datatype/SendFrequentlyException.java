package org.firas.sms.datatype;

import java.util.Date;
import lombok.Getter;
import org.firas.sms.model.SmsLimit;

public class SendFrequentlyException extends RuntimeException {

    @Getter private SmsLimit smsLimit;
    @Getter private Date lastTime;

    public SendFrequentlyException(SmsLimit smsLimit, Date lastTime) {
        this(smsLimit, lastTime,
                "在" + smsLimit.descPeriod(null, null, null) +
                "内最多只能向同一个手机号发送" +
                smsLimit.getMaxCount() + "条短信");
    }

    public SendFrequentlyException(SmsLimit smsLimit,
            Date lastTime, String message) {
        super(message);
        this.smsLimit = smsLimit;
        this.lastTime = lastTime;
    }

}
