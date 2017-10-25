package org.firas.sms.datatype;

public class PeriodOccupiedException extends RuntimeException {

    private Integer templateId;
    private Integer period;

    public PeriodOccupiedException(Integer templateId, Integer period) {
        this(templateId, period, "ID为" + templateId + "的短信模板的" +
                period + "秒内的发送限制已存在");
    }

    public PeriodOccupiedException(Integer templateId, Integer period,
            String message) {
        super(message);
        this.templateId = templateId;
        this.period = period;
    }

}
