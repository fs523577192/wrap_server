package org.firas.common.helper;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.lang.reflect.Type;
import java.lang.reflect.ParameterizedType;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;

import org.firas.common.datatype.reflect.MyClass;
import org.firas.common.datatype.reflect.OrdinaryClass;
import org.firas.common.datatype.reflect.GenericCollection;
import org.firas.common.datatype.reflect.GenericMap;

@Slf4j
public class GenericClassHelper {

    @SuppressWarnings("unchecked")
    public static  <T extends Map, K, V>  T toMap(
            GenericMap<T, K, V> mapClass, Object obj) {
        if (!isGenericMap(mapClass, obj)) throw new ClassCastException();
        return (T)obj;
    }

    @SuppressWarnings("unchecked")
    public static  <T extends Collection, E>  T toCollection(
            GenericCollection<T, E> collectionClass, Object obj) {
        if (!isGenericCollection(collectionClass, obj)) {
            throw new ClassCastException();
        }
        return (T)obj;
    }

    @SuppressWarnings("unchecked")
    public static boolean isGenericCollection(
            GenericCollection genericCollection, Object obj) {
        if (null == obj) return true;
        Class realClass = obj.getClass();
        Class mainClass = genericCollection.getMain();
        if (!mainClass.isAssignableFrom(realClass)) return false;

        MyClass temp = genericCollection.getGeneric();
        if (temp instanceof GenericCollection) {
            GenericCollection collectionClass = (GenericCollection)temp;
            for (Object item : (Collection)obj) {
                if (!isGenericCollection(collectionClass, item)) return false;
            }
        } else if (temp instanceof GenericMap) {
            GenericMap mapClass = (GenericMap)temp;
            for (Object item : (Collection)obj) {
                if (!isGenericMap(mapClass, item)) return false;
            }
        } else {
            Class otherClass = temp.getMain();
            for (Object item : (Collection)obj) {
                if (null != item && !otherClass.isInstance(item)) return false;
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    public static boolean isGenericMap(GenericMap genericMap, Object obj) {
        if (null == obj) return true;
        Class realClass = obj.getClass();
        Class mainClass = genericMap.getMain();
        if (!mainClass.isAssignableFrom(realClass)) return false;

        Map map = (Map)obj;
        Set keySet = map.keySet();
        Collection values = map.values();

        MyClass temp = genericMap.getKey();
        if (temp instanceof GenericCollection) {
            GenericCollection collectionClass = (GenericCollection)temp;
            for (Object item : keySet) {
                if (!isGenericCollection(collectionClass, item)) return false;
            }
        } else if (temp instanceof GenericMap) {
            GenericMap mapClass = (GenericMap)temp;
            for (Object item : keySet) {
                if (!isGenericMap(mapClass, item)) return false;
            }
        } else {
            Class otherClass = temp.getMain();
            for (Object item : keySet) {
                if (null != item && !otherClass.isInstance(item)) return false;
            }
        }

        temp = genericMap.getValue();
        if (temp instanceof GenericCollection) {
            GenericCollection collectionClass = (GenericCollection)temp;
            for (Object item : values) {
                if (!isGenericCollection(collectionClass, item)) return false;
            }
        } else if (temp instanceof GenericMap) {
            GenericMap mapClass = (GenericMap)temp;
            for (Object item : values) {
                if (!isGenericMap(mapClass, item)) return false;
            }
        } else {
            Class otherClass = temp.getMain();
            for (Object item : values) {
                if (null != item && !otherClass.isInstance(item)) return false;
            }
        }
        return true;
    }

    public static final ParameterizedType STRING_OBJECT_MAP_TYPE;
    public static final ParameterizedType STRING_OBJECT_HASHMAP_TYPE;
    public static final ParameterizedType STRING_OBJECT_MAP_LIST_TYPE;
    public static final ParameterizedType STRING_OBJECT_MAP_MAP_TYPE;

    static {
        TypeReference<Map<String, Object>> temp1 =
                new TypeReference<Map<String, Object>>(){};
        STRING_OBJECT_MAP_TYPE = (ParameterizedType)temp1.getType();

        TypeReference<HashMap<String, Object>> temp2 =
                new TypeReference<HashMap<String, Object>>(){};
        STRING_OBJECT_HASHMAP_TYPE = (ParameterizedType)temp2.getType();

        TypeReference<List<Map<String, Object>>> temp3 =
                new TypeReference<List<Map<String, Object>>>(){};
        STRING_OBJECT_MAP_LIST_TYPE = (ParameterizedType)temp3.getType();

        TypeReference<Map<String, Map<String, Object>>> temp4 =
                new TypeReference<Map<String, Map<String, Object>>>(){};
        STRING_OBJECT_MAP_MAP_TYPE = (ParameterizedType)temp4.getType();
    }

    public static enum TypeType {
        UNKNOWN, ORDINARY, GENERIC_COLLECTION, GENERIC_MAP
    };

    private static TypeType recognize(Type type) {
        if (type instanceof Class) return TypeType.ORDINARY;
        if (type instanceof ParameterizedType) {
            ParameterizedType tempType = ParameterizedType.class.cast(type);
            type = tempType.getRawType();
            if (!(type instanceof Class)) return TypeType.UNKNOWN;

            Class tempClass = Class.class.cast(type);
            Type[] types = tempType.getActualTypeArguments();
            if (Collection.class.isAssignableFrom(tempClass)) {
                if (1 == types.length) return TypeType.GENERIC_COLLECTION;
            } else if (Map.class.isAssignableFrom(tempClass)) {
                if (2 == types.length) return TypeType.GENERIC_MAP;
            }
        }
        return TypeType.UNKNOWN;
    }

    public static boolean isGenericCollection(
        ParameterizedType type,
        Object obj
    ) throws RuntimeException {
        if (!recognize(type).equals(TypeType.GENERIC_COLLECTION)) {
            throw new IllegalArgumentException(
                    "The type is not a parameterized type of a collection");
        }

        if (null == obj) return true;
        Class collectionClass = Class.class.cast(type.getRawType());
        if (!collectionClass.isInstance(obj)) return false;
        Collection collection = Collection.class.cast(obj);

        Type[] types = type.getActualTypeArguments();
        TypeType typeType = recognize(types[0]);
        if (typeType.equals(TypeType.UNKNOWN)) {
            throw new RuntimeException("Unsupported type");
        }
        if (typeType.equals(TypeType.ORDINARY)) {
            Class elementClass = Class.class.cast(types[0]);
            for (Object item : collection) {
                if (null != item && !elementClass.isInstance(item)) {
                    return false;
                }
            }
        } else if (typeType.equals(TypeType.GENERIC_COLLECTION)) {
            type = ParameterizedType.class.cast(types[0]);
            for (Object item : collection) {
                if (!isGenericCollection(type, item)) return false;
            }
        } else if (typeType.equals(TypeType.GENERIC_MAP)) {
            type = ParameterizedType.class.cast(types[0]);
            for (Object item : collection) {
                if (!isGenericMap(type, item)) return false;
            }
        }
        return true;
    }

    public static boolean isGenericMap(
        ParameterizedType type,
        Object obj
    ) throws RuntimeException {
        if (!recognize(type).equals(TypeType.GENERIC_MAP)) {
            throw new IllegalArgumentException(
                    "The type is not a parameterized type of a map");
        }

        if (null == obj) return true;
        Class mapClass = Class.class.cast(type.getRawType());
        if (!mapClass.isInstance(obj)) return false;
        Map map = Map.class.cast(obj);

        Type[] types = type.getActualTypeArguments();
        TypeType keyType = recognize(types[0]);
        TypeType valueType = recognize(types[1]);
        if (
            keyType.equals(TypeType.UNKNOWN) ||
            valueType.equals(TypeType.UNKNOWN)
        ) {
            throw new RuntimeException("Unsupported type");
        }
        if (keyType.equals(TypeType.ORDINARY)) {
            Class elementClass = Class.class.cast(types[0]);
            for (Object item : map.keySet()) {
                if (null != item && !elementClass.isInstance(item)) {
                    return false;
                }
            }
        } else if (keyType.equals(TypeType.GENERIC_COLLECTION)) {
            type = ParameterizedType.class.cast(types[0]);
            for (Object item : map.keySet()) {
                if (!isGenericCollection(type, item)) return false;
            }
        } else if (keyType.equals(TypeType.GENERIC_MAP)) {
            type = ParameterizedType.class.cast(types[0]);
            for (Object item : map.keySet()) {
                if (!isGenericMap(type, item)) return false;
            }
        }
        if (valueType.equals(TypeType.ORDINARY)) {
            Class elementClass = Class.class.cast(types[1]);
            for (Object item : map.values()) {
                if (null != item && !elementClass.isInstance(item)) {
                    return false;
                }
            }
        } else if (valueType.equals(TypeType.GENERIC_COLLECTION)) {
            type = ParameterizedType.class.cast(types[1]);
            for (Object item : map.values()) {
                if (!isGenericCollection(type, item)) return false;
            }
        } else if (valueType.equals(TypeType.GENERIC_MAP)) {
            type = ParameterizedType.class.cast(types[1]);
            for (Object item : map.values()) {
                if (!isGenericMap(type, item)) return false;
            }
        }
        return true;
    }


    @SuppressWarnings("unchecked")
    public static Map<String, Object> toStringObjectMap(
        Object obj
    ) throws RuntimeException {
        if (!isGenericMap(STRING_OBJECT_MAP_TYPE, obj)) {
            throw new ClassCastException("Cannot convert obj to " +
                    "java.util.Map<java.lang.String, java.lang.Object>");
        }
        return (Map<String, Object>)obj;
    }

    @SuppressWarnings("unchecked")
    public static HashMap<String, Object> toStringObjectHashMap(
        Object obj
    ) throws RuntimeException {
        if (!isGenericMap(STRING_OBJECT_HASHMAP_TYPE, obj)) {
            throw new ClassCastException("Cannot convert obj to " +
                    "java.util.HashMap<java.lang.String, java.lang.Object>");
        }
        return (HashMap<String, Object>)obj;
    }

    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> toStringObjectMapList(
        Object obj
    ) throws RuntimeException {
        if (!isGenericCollection(STRING_OBJECT_MAP_LIST_TYPE, obj)) {
            throw new ClassCastException("Cannot convert obj to " +
                    "java.util.List<java.util.Map<" +
                    "java.lang.String, java.lang.Object>>");
        }
        return (List<Map<String, Object>>)obj;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Map<String, Object>> toStringObjectMapMap(
        Object obj
    ) throws RuntimeException {
        if (!isGenericMap(STRING_OBJECT_MAP_MAP_TYPE, obj)) {
            throw new ClassCastException("Cannot convert obj to " +
                    "java.util.Map<java.lang.String, java.util.Map<" +
                    "java.lang.String, java.lang.Object>>");
        }
        return (Map<String, Map<String, Object>>)obj;
    }

}
