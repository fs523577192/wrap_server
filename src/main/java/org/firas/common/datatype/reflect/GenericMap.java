package org.firas.common.datatype.reflect;

import java.util.Map;
import java.util.HashMap;
import lombok.Getter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GenericMap<T extends Map, K, V> implements MyClass<T> {

    public static final GenericMap<Map, String, Object>
            STRING_OBJECT_MAP = new GenericMap<Map, String, Object>(
                    Map.class, OrdinaryClass.STRING, OrdinaryClass.OBJECT);
    public static final GenericMap<HashMap, String, Object>
            STRING_OBJECT_HASHMAP = new GenericMap<HashMap, String, Object>(
                    HashMap.class, OrdinaryClass.STRING, OrdinaryClass.OBJECT);

    @Getter private Class<T> main;
    @Getter private MyClass<K> key;
    @Getter private MyClass<V> value;
}
