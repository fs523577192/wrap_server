package org.firas.common.datatype.reflect;

import java.util.Collection;
import lombok.Getter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GenericCollection<T extends Collection, E> implements MyClass<T> {

    @Getter private Class<T> main;
    @Getter private MyClass<E> generic;
}
