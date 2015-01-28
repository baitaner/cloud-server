package com.baitaner.common.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.*;

@Repository("redisCache")
public class RedisCacheImpl implements com.baitaner.common.cache.ICache {

    @Autowired
    private RedisTemplate<Serializable, Serializable> redisTemplate;

    /* (non-Javadoc)
     * @see com.silveroaklabs.common.dao.IRedisCache#save(java.lang.String, java.lang.String)
     */
    @Override
    public void put(final String key, final String value) {
        redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.set(
                        serialize(key),
                        serialize(value));
                return null;
            }
        });
    }

    @Override
    public void putEx(final String key, final long seconds, final String value) {
        redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.setEx(
                        serialize(key),
                        seconds,
                        serialize(value));
                return null;
            }
        });
    }

    /* (non-Javadoc)
     * @see com.silveroaklabs.common.dao.IRedisCache#read(java.lang.String)
     */
    @Override
    public String get(final String key) {
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] keyByte = serialize(key);
                byte[] value = connection.get(keyByte);
                String valueStr = deserialize(value);
                return valueStr;
            }
        });
    }

    /* (non-Javadoc)
     * @see com.silveroaklabs.common.dao.IRedisCache#remove(java.lang.String)
     */
    @Override
    public void delete(final String key) {
        redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) {
                connection.del(serialize(key));
                return null;
            }
        });

    }

    //Set save read remove
    /* (non-Javadoc)
     * @see com.silveroaklabs.common.dao.IRedisCache#save(java.lang.String, java.util.Set)
	 */
    @Override
    public void put(final String key, final Set<String> valueSet) {
        redisTemplate.execute(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doInRedis(RedisConnection connection)
                    throws DataAccessException {
                for (String value : valueSet) {
                    connection.sAdd(serialize(key), serialize(value));
                }
                return null;
            }
        });
    }


    /* (non-Javadoc)
     * @see com.silveroaklabs.common.dao.IRedisCache#saveToSet(java.lang.String, java.lang.String)
     */
    @Override
    public void putToSet(final String key, final String value) {
        redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.sAdd(serialize(key), serialize(value));
                return null;
            }
        });

    }

    @Override
    public String popFromSet(final String key) {
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] keyByte = serialize(key);
                byte[] value = connection.sPop(keyByte);
                if (value != null) {
                    return deserialize(value);
                }
                return null;
            }
        });
    }

    /* (non-Javadoc)
     * @see com.silveroaklabs.common.dao.IRedisCache#readFromSet(java.lang.String)
     */
    @Override
    public String getFromSet(final String key) {
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] keyByte = serialize(key);
                byte[] value = connection.sRandMember(keyByte);
                if (value != null) {
                    return deserialize(value);
                }
                return null;
            }
        });
    }


    /* (non-Javadoc)
     * @see com.silveroaklabs.common.dao.IRedisCache#readSet(java.lang.String)
     */
    @Override
    public Set<String> getSet(final String key) {
        return redisTemplate.execute(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] keyByte = serialize(key);
                Set<String> resultSet = new HashSet<String>();
                Set<byte[]> values = connection.sMembers(keyByte);
                for (byte[] vbyte : values) {
                    resultSet.add(deserialize(vbyte));
                }
                return resultSet;
            }
        });
    }

    /* (non-Javadoc)
     * @see com.silveroaklabs.common.dao.IRedisCache#readSetLen(java.lang.String)
     */
    @Override
    public Long getSetLen(final String key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] keyByte = serialize(key);
                Long len = connection.sCard(keyByte);
                return len;
            }
        });
    }

    /* (non-Javadoc)
     * @see com.silveroaklabs.common.dao.IRedisCache#removeFromSet(java.lang.String, java.lang.String)
     */
    @Override
    public Long deleteFromSet(final String key, final String value) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] keyByte = serialize(key);
                byte[] valueByte = serialize(value);
                return connection.sRem(keyByte, valueByte);
            }
        });
    }


    //List save read remove
	/* (non-Javadoc)
	 * @see com.silveroaklabs.common.dao.IRedisCache#save(java.lang.String, java.util.List)
	 */
    @Override
    public void put(final String key, final List<String> valueList) {
        redisTemplate.execute(new RedisCallback<List<String>>() {
            @Override
            public List<String> doInRedis(RedisConnection connection)
                    throws DataAccessException {
                for (String value : valueList) {
                    connection.lPush(serialize(key), serialize(value));
                }
                return null;
            }
        });

    }

    /* (non-Javadoc)
     * @see com.silveroaklabs.common.dao.IRedisCache#saveToList(java.lang.String, java.lang.String)
     */
    @Override
    public void putToList(final String key, final String value) {
        redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.lPush(serialize(key), serialize(value));
                return null;
            }
        });
    }

    /* (non-Javadoc)
     * @see com.silveroaklabs.common.dao.IRedisCache#readFromList(java.lang.String, int)
     */
    @Override
    public String getFromList(final String key, final long index) {
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] keyByte = serialize(key);
                long realLen = connection.lLen(keyByte);
                if (realLen <= index) {
                    return null;
                }

                byte[] value = connection.lIndex(keyByte, index);
                if (value != null) {
                    return deserialize(value);
                }

                return null;
            }
        });
    }


    /* (non-Javadoc)
     * @see com.silveroaklabs.common.dao.IRedisCache#readList(java.lang.String, int, int)
     */
    @Override
    public List<String> getList(final String key, final long start, final long end) {
        return redisTemplate.execute(new RedisCallback<List<String>>() {
            @Override
            public List<String> doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] keyByte = serialize(key);
                List<String> resultList = new ArrayList<String>();
                long realLen = connection.lLen(keyByte);
                List<byte[]> values = null;
                if (realLen < end) {
                    values = connection.lRange(keyByte, start, realLen - 1);
                } else {
                    values = connection.lRange(keyByte, start, end);
                }

                for (byte[] vbyte : values) {
                    resultList.add(deserialize(vbyte));
                }
                return resultList;
            }
        });
    }

    /* (non-Javadoc)
     * @see com.silveroaklabs.common.dao.IRedisCache#readListLen(java.lang.String)
     */
    @Override
    public Long getListLen(final String key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] keyByte = serialize(key);
                Long len = connection.lLen(keyByte);
                return len;
            }
        });
    }

    /* (non-Javadoc)
     * @see com.silveroaklabs.common.dao.IRedisCache#removeFromList(java.lang.String, java.lang.String, int)
     */
    @Override
    public Long deleteFromList(final String key, final String value, final long count) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] keyByte = serialize(key);
                byte[] valueByte = serialize(value);
                return connection.lRem(keyByte, count, valueByte);
            }
        });
    }


    //Map save read remove
	/* (non-Javadoc)
	 * @see com.silveroaklabs.common.dao.IRedisCache#save(java.lang.String, java.util.Map)
	 */
    @Override
    public void put(final String key, final Map<String, String> map) {
        redisTemplate.execute(new RedisCallback<Map<String, String>>() {
            @Override
            public Map<String, String> doInRedis(RedisConnection connection)
                    throws DataAccessException {
                Map<byte[], byte[]> byteMap = new HashMap<byte[], byte[]>();
                for (String k : map.keySet()) {
                    byteMap.put(serialize(k), serialize(map.get(k)));
                }
                connection.hMSet(serialize(key), byteMap);
                return null;
            }
        });

    }

    /* (non-Javadoc)
	 * @see com.silveroaklabs.common.dao.IRedisCache#saveToList(java.lang.String, java.lang.String)
	 */
    @Override
    public void putToMap(final String key, final String mapKey, final String value) {
        redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.hSet(serialize(key), serialize(mapKey), serialize(value));
                return null;
            }
        });
    }

    /* (non-Javadoc)
     * @see com.silveroaklabs.common.dao.IRedisCache#readFromMap(java.lang.String, java.lang.String)
     */
    @Override
    public String getFromMap(final String key, final String mapKey) {
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] keyByte = serialize(key);
                byte[] mapKeyByte = serialize(mapKey);

                byte[] value = connection.hGet(keyByte, mapKeyByte);
                return deserialize(value);
            }
        });
    }


    /* (non-Javadoc)
     * @see com.silveroaklabs.common.dao.IRedisCache#readMap(java.lang.String)
     */
    @Override
    public Map<String, String> getMap(final String key) {
        return redisTemplate.execute(new RedisCallback<Map<String, String>>() {
            @Override
            public Map<String, String> doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] keyByte = serialize(key);
                Map<String, String> reMap = new HashMap<String, String>();
                Map<byte[], byte[]> valueMap = connection.hGetAll(keyByte);
                for (byte[] vbyte : valueMap.keySet()) {
                    reMap.put(deserialize(vbyte), deserialize(valueMap.get(vbyte)));
                }
                return reMap;
            }
        });
    }

    /* (non-Javadoc)
     * @see com.silveroaklabs.common.dao.IRedisCache#readMapLen(java.lang.String)
     */
    @Override
    public Long getMapLen(final String key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] keyByte = serialize(key);
                Long len = connection.hLen(keyByte);
                return len;
            }
        });
    }

    /* (non-Javadoc)
     * @see com.silveroaklabs.common.dao.IRedisCache#removeFromMap(java.lang.String, java.lang.String)
     */
    @Override
    public Long deleteFromMap(final String key, final String value) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] keyByte = serialize(key);
                byte[] valueByte = serialize(value);
                return connection.hDel(keyByte, valueByte);
            }
        });
    }

    public byte[] serialize(String value) {
        return this.redisTemplate.getStringSerializer().serialize(value);
    }

    public String deserialize(byte[] value) {
        return this.redisTemplate.getStringSerializer().deserialize(value);


    }

    /* (non-Javadoc)
     * @see com.silveroaklabs.common.cache.ICache#mGet(java.lang.String[])
     */
    @Override
    public List<String> mGet(final String[] keys) {

        return redisTemplate.execute(new RedisCallback<List<String>>() {
            @Override
            public List<String> doInRedis(RedisConnection connection)
                    throws DataAccessException {
                List<byte[]> keyByteList = new ArrayList<byte[]>();
                for (String key : keys) {
                    byte[] keyByte = serialize(key);
                    keyByteList.add(keyByte);
                }

                List<String> valueList = new ArrayList<String>();
                List<byte[]> valueByteList = connection.mGet(keyByteList.toArray(new byte[0][]));
                for (byte[] valueByte : valueByteList) {
                    String valueStr = deserialize(valueByte);
                    valueList.add(valueStr);
                }

                return valueList;
            }
        });
    }


}
