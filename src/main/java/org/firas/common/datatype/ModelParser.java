package org.firas.common.datatype;

import java.util.HashMap;

public interface ModelParser<T> {

    public HashMap<String, Object> parse(T model);
}
