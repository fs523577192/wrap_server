package org.firas.common.helper;

import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;

public class FutureHelper {

    public static <T> T get(Future<T> task) throws Exception {
        try {
            return task.get();
        } catch (ExecutionException ex) {
            if (ex.getCause() instanceof Exception) {
                throw Exception.class.cast(ex.getCause());
            }
            throw ex;
        }
    }

}
