package org.firas.common.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateTimeValidator extends Validator {

    @Getter @Setter protected Date min, max;
    @Getter @Setter protected String message, messageMin, messageMax;

    protected static final String DEFAULT_MESSAGE =
                    "The input is not a valid date/time in the specific format",
            DEFAULT_MESSAGE_MIN =
                    "The input is not a date/time after the specific time",
            DEFAULT_MESSAGE_MAX =
                    "The input is not a date/time before the specific time";

    public static final String CODE = "DateTime",
            CODE_MIN = "DateTime.min",
            CODE_MAX = "DateTime.max";

    protected String code = CODE,
            codeMin = CODE_MIN,
            codeMax = CODE_MAX;


    protected Date converted;
    protected SimpleDateFormat formatter;


    public DateTimeValidator(String pattern) {
        this(pattern, DEFAULT_MESSAGE);
    }
    
    public DateTimeValidator(String pattern, String message) {
        this(pattern, message, null, null);
    }

    public DateTimeValidator(String pattern, Date min, Date max) {
        this(pattern, DEFAULT_MESSAGE, min, max);
    }

    public DateTimeValidator(String pattern, String message,
            Date min, Date max) {
        this(pattern, message, min, DEFAULT_MESSAGE_MIN,
                max, DEFAULT_MESSAGE_MAX);
    }

    public DateTimeValidator(String pattern, String message, Date min,
            String messageMin, Date max, String messageMax) {
        setPattern(pattern);
        this.message = message;
        this.min = min;
        this.messageMin = messageMin;
        this.max = max;
        this.messageMax = messageMax;
        this.converted = null;
    }


    public boolean convertType() {
        return true;
    }

    public Object getConverted() {
        return converted;
    }

    public void setPattern(String pattern) {
        formatter = new SimpleDateFormat(pattern);
    }

    public String getPattern() {
        return null == formatter ? null : formatter.toPattern();
    }

    public boolean validate(String str) {
        try {
            boolean result = true;
            converted = formatter.parse(str);
            if (null != min && converted.compareTo(min) < 0) {
                this.errors.add(new ValidationError(codeMin, messageMin));
                if (this.onlyOneError) return false;
                result = false;
            }
            if (null != max && converted.compareTo(max) > 0) {
                this.errors.add(new ValidationError(codeMax, messageMax));
                return false;
            }
            return result;
        } catch (NullPointerException e) {
        } catch (ParseException e) {
        }
        this.errors.add(new ValidationError(code, message));
        return false;
    }

}
