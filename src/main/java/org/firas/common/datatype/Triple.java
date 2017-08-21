package org.firas.common.datatype;

import lombok.Getter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Triple<T1, T2, T3> {

    @Getter private T1 item1;
    @Getter private T2 item2;
    @Getter private T3 item3;
}
