package org.firas.common.dao;

import java.util.concurrent.TimeUnit;

public interface RedisDao<K, V> {

    public V getByKey(K key);

    public void setByKey(K key, V value);
    public void setByKey(K key, V value, long timeout, TimeUnit unit);

    public boolean keyExists(K key);

    public void removeByKey(K key);
}
