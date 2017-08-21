package org.firas.common.datatype;

import lombok.Getter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Pair<T1, T2> {

    @Getter private T1 item1;
    @Getter private T2 item2;
}
