package org.firas.common.helper;

import java.util.List;
import java.lang.reflect.Field;
import java.lang.annotation.Annotation;
import javax.persistence.Table;
import javax.persistence.Query;
import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.data.jpa.repository.support.DefaultJpaContext;
import org.springframework.util.MultiValueMap;

public class JpaHelper {

    @SuppressWarnings("unchecked")
    public static String getEntityTableName(Class entityClass) {
        Table annotation = Table.class.cast(entityClass.getAnnotation(
                Table.class));
        return (null == annotation) ? null : annotation.name();
    }

    @SuppressWarnings("unchecked")
    public static EntityManager getEntityManagerFor(JpaContext context, Class entityClass) {
        String tableName = getEntityTableName(entityClass);
        if (null == tableName) return null;
        try {
            Field field = DefaultJpaContext.class.getDeclaredField("entityManagers");
            field.setAccessible(true);
            Object temp = field.get(context);
            MultiValueMap<Class, EntityManager> entityManagers =
                    (MultiValueMap<Class, EntityManager>)temp;
            List<EntityManager> candidates = (List<EntityManager>)
                    entityManagers.get(entityClass);
            if (null == candidates) return null;
            for (EntityManager manager : candidates) {
                Query query = manager.createNativeQuery("SHOW TABLES");
                if (query.getResultList().contains(tableName)) {
                    return manager;
                }
            }
        } catch (NoSuchFieldException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
