package org.firas.common.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.HashMap;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.firas.common.helper.DateTimeHelper;
import org.firas.common.helper.TrueValueHelper;
import org.firas.common.helper.CamelUnderscoreHelper;
import org.firas.common.helper.EmptyNullConverter;

@Slf4j
@MappedSuperclass
public abstract class TimeModel implements Serializable {
    @Transient
    private static final long serialVersionUID = 1L;

    /**
     * 设置属性
     * 对于字符串属性，如果value为null则不设置，如果为空字符串则设为null
     */
    @SuppressWarnings("unchecked")
    public TimeModel setField(
            Map<String, Object> change,
            String name,
            Object value
    ) throws ReflectiveOperationException {
        String fieldName = CamelUnderscoreHelper.underscore2Camel(name);
        String getMethodName = "get" + Character.toUpperCase(
                fieldName.charAt(0)) + fieldName.substring(1);
        String setMethodName = 's' + getMethodName.substring(1);

        Class thisClass = this.getClass();
        Field field = null;
        NoSuchFieldException firstException = null;
        for (
            Class currentClass = thisClass;
            null != currentClass;
            currentClass = currentClass.getSuperclass()
        ) {
            try {
                field = currentClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException ex) {
                if (null == firstException) firstException = ex;
                continue;
            }
            break;
        }
        if (null == field) throw firstException;

        Class fieldClass = field.getType();
        Column annotation = Column.class.cast(
                field.getAnnotation(Column.class));

        if (String.class.isAssignableFrom(fieldClass)) {
            if (null == value) return this;
            String temp = String.class.cast(value);
            if (temp.isEmpty()) value = null;
        }

        Method getMethod = thisClass.getMethod(getMethodName);
        Method setMethod = thisClass.getMethod(setMethodName, fieldClass);
        Object currentValue = getMethod.invoke(this);
        if (null != value) {
            if (!value.equals(currentValue)) {
                setMethod.invoke(this, value);
                change.put(name, value);
            }
        } else if (annotation.nullable() && null != currentValue) {
            setMethod.invoke(this, value);
            change.put(name, null);
        }
        return this;
    }

    public HashMap<String, Object> toMap(Map<String, Object> options) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        if (TrueValueHelper.isTrue(options, "create_time")) {
            result.put("create_time", this.getCreateTime().getTime());
        }
        if (TrueValueHelper.isTrue(options, "update_time")) {
            result.put("update_time", this.getUpdateTime().getTime());
        }
        
        Object obj = options.get("create_time_info");
        if (obj instanceof DateFormat) {
            result.put("create_time_info", this.getCreateTimeInfo(
                    (DateFormat)obj));
        }
        obj = options.get("update_time_info");
        if (obj instanceof DateFormat) {
            result.put("update_time_info", this.getUpdateTimeInfo(
                    (DateFormat)obj));
        }
        return result;
    }
    
    @Column(name = "create_time", nullable = false)
    @Getter @Setter protected Timestamp createTime;
    
    public String getCreateTimeInfo(DateFormat formatter) {
        if (null == formatter) formatter =
                DateTimeHelper.getDateTimeFormatterWithTAndZone();
        return formatter.format(createTime);
    }

    @Column(name = "update_time", nullable = false)
    @Getter @Setter protected Timestamp updateTime;
    
    public String getUpdateTimeInfo(DateFormat formatter) {
        if (null == formatter) formatter =
                DateTimeHelper.getDateTimeFormatterWithTAndZone();
        return formatter.format(updateTime);
    }
    
    @PrePersist
    protected void beforeCreate() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        setCreateTime(now);
        setUpdateTime(now);
    }

    @PreUpdate
    protected void beforeUpdate() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        setUpdateTime(now);
    }
}
