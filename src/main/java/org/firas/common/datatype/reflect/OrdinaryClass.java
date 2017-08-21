package org.firas.common.datatype.reflect;

import lombok.Getter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrdinaryClass<T> implements MyClass<T> {

    public static final OrdinaryClass<Object> OBJECT =
            new OrdinaryClass<Object>(Object.class);
    public static final OrdinaryClass<Integer> INTEGER =
            new OrdinaryClass<Integer>(Integer.class);
    public static final OrdinaryClass<Long> LONG =
            new OrdinaryClass<Long>(Long.class);
    public static final OrdinaryClass<String> STRING =
            new OrdinaryClass<String>(String.class);

    @Getter private Class<T> main;
}
