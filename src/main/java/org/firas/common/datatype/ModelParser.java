package org.firas.common.datatype;

import java.util.HashMap;

public interface ModelParser<T> {

    HashMap<String, Object> parse(T model);
}
