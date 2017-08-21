package org.firas.common.dao;

import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationUtils;
import org.springframework.data.redis.serializer.SerializationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

@Repository("redisDao")
public class DefaultRedisDao<K, V> implements RedisDao<K, V> {

    public static final String PREFIX = "data.";
    public static final String KEYS_KEY = "keys";

    private static final byte[] EMPTY_ARRAY = new byte[0];
    private static boolean isEmpty(byte[] data) {
        return (null == data || data.length == 0);
    }

    protected RedisTemplate<String, V> template;
    protected ValueOperations<String, V> operation;

    @Autowired
    public DefaultRedisDao(RedisConnectionFactory factory) {
        this.template = new RedisTemplate<String, V>();
        this.template.setValueSerializer(new RedisSerializer<V>() {
            private ObjectMapper objectMapper = new ObjectMapper();

            public byte[] serialize(Object v) throws SerializationException {
                if (null == v) return EMPTY_ARRAY;
                try {
                    return objectMapper.writeValueAsBytes(v);
                } catch (Exception ex) {
                    throw new SerializationException("Could not write JSON: " +
                            ex.getMessage(), ex);
                }
            }
            public V deserialize(byte[] bytes) throws SerializationException {
                if (isEmpty(bytes)) return null;
                try {
                    return objectMapper.readValue(bytes,
                            new TypeReference<V>(){});
                } catch (Exception ex) {
                    throw new SerializationException("Could not read JSON: " +
                            ex.getMessage(), ex);
                }
            }
        });
        this.template.setConnectionFactory(factory);
        this.template.setEnableTransactionSupport(true);
        this.template.afterPropertiesSet();
        this.operation = this.template.opsForValue();
    }


    protected String getRealKey(K key) {
        return PREFIX + key.toString();
    }

    public V getByKey(K key) {
        return operation.get(this.getRealKey(key));
    }

    public void setByKey(K key, V value) {
        operation.set(this.getRealKey(key), value);
    }

    public void setByKey(K key, V value, long timeout, TimeUnit unit) {
        operation.set(this.getRealKey(key), value, timeout, unit);
    }

    public boolean keyExists(K key) {
        return template.hasKey(this.getRealKey(key));
    }

    public void removeByKey(K key) {
        template.delete(this.getRealKey(key));
    }
}
