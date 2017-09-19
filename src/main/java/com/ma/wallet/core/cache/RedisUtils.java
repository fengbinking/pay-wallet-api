/**
 * Project Name:mababy
 * File Name:RedisCache.java
 * Package Name:com.soa.common.cache
 * Date:2015年12月18日下午2:17:17
 * Copyright (c) 2015, modernavenue.com All Rights Reserved.
 */

package com.ma.wallet.core.cache;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.collect.Lists;
import com.ma.wallet.core.service.IRedisCallback;
import com.ma.wallet.core.utils.BeanUtil;
import com.ma.wallet.core.utils.JsonUtil;
import com.ma.wallet.core.utils.PageUtil;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;

import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class RedisUtils {

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisUtils.class);

    private static StringRedisTemplate redisTemplate;

    static {
        redisTemplate = BeanUtil.getAppContext().getBean("stringRedisTemplate", StringRedisTemplate.class);
        LOGGER.info("redisTemplate==null:" + (redisTemplate == null));
    }

    /**
     * 删除缓存记录
     *
     * @param key
     */
    public static void delete(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            handleJedisException(e);
        }
    }

    /**
     * 设置对象类型缓存项，无失效时间
     *
     * @param key
     * @param value
     */
    public static <V> boolean set(String key, V value) {
        return set(key, value, -1);
    }

    /**
     * 设置key-value项，字节类型
     *
     * @param key
     * @param value
     */
    public static <V> boolean set(String key, V value, int exp) {
        try {
            String serialize = serialize(value);
            if (exp > 0) {
                redisTemplate.opsForValue().set(key, serialize, exp, TimeUnit.SECONDS);
            } else {
                redisTemplate.opsForValue().set(key, serialize);
            }
        } catch (Exception e) {
            handleJedisException(e);
            return false;
        }
        return true;
    }
    
   

    /**
     * 获取对象类型
     *
     * @param key
     * @return
     */
    public static <V> V get(String key, Class<V> v) {
        String data = getString(key);
        if (data != null) {
            return deserialize(data, v);
        }
        return null;
    }

    /**
     * 获取对象类型
     *
     * @param key
     * @return
     */
    public static <V> V get(String key, Class<V> v, IRedisCallback t) {
        String data = getString(key);
        if (data != null) {
            return deserialize(data, v);
        }
        Object obj = t.callback();
        if (obj != null) {
            set(key, obj);
            return (V) obj;
        }
        set(key, newInstance(v), RandomUtils.nextInt(5, 10));
        return null;
    }

    /**
     * 获取对象类型
     *
     * @param key
     * @return
     */
    public static <V> V get(String key, Class<V> v, int seconds, IRedisCallback t) {
        String data = getString(key);
        if (data != null) {
            return deserialize(data, v);
        }
        Object obj = t.callback();
        if (obj != null) {
            set(key, obj, seconds);
            return (V) obj;
        }
        set(key, newInstance(v), RandomUtils.nextInt(5, 10));
        return null;
    }

    /**
     * 获取对象类型
     *
     * @param key
     * @return
     */
    public static <V> List<V> getList(String key, Class<V> v) {
        String data = getString(key);
        if (data != null) {
            return JsonUtil.fromJson(data, TypeFactory.defaultInstance().constructCollectionType(List.class, v));
        }
        return null;
    }

    public static <V> List<V> getList(String key, Class<V> v, IRedisCallback t) {
        String data = getString(key);
        if (StringUtils.isNotBlank(data)) {
            return JsonUtil.fromJson(data, TypeFactory.defaultInstance().constructCollectionType(List.class, v));
        }
        Object obj = t.callback();
        if (obj != null) {
            set(key, obj);
            return (List<V>) obj;
        }
        V newInstance = newInstance(v);
        set(key, newInstance, RandomUtils.nextInt(5, 10));
        return null;
    }

    public static <V> List<V> getList(String key, Class<V> v, int expireSeconds, IRedisCallback t) {
        String data = getString(key);
        if (StringUtils.isNotBlank(data)) {
            return JsonUtil.fromJson(data, TypeFactory.defaultInstance().constructCollectionType(List.class, v));
        }
        Object obj = t.callback();
        if (obj != null) {
            set(key, obj, expireSeconds);
            return (List<V>) obj;
        }
        V newInstance = newInstance(v);
        set(key, Arrays.asList(newInstance), RandomUtils.nextInt(5, 10));
        return null;
    }

    private static <V> V newInstance(Class<V> v) {
        V newInstance = null;
        try {
            newInstance = v.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            // e.printStackTrace();
        }
        return newInstance;
    }

    /**
     * 获取字符串类型
     *
     * @param key
     * @return
     */
    public static String getString(String key) {
        try {
            String value = redisTemplate.opsForValue().get(key);
            return value;
        } catch (Exception e) {
            handleJedisException(e);
        }
        return null;
    }
    
    
    /**
     * 新增list
     *
     * @param listKey
     * @return
     */
    public static <T> Long rightPushAll(String listKey, List<T> list) {
    	
    	List<String> result = Lists.newArrayList();
    	
    	if (list != null) {
            for (T value : list) {
                result.add(serialize(value));
            }
        }
        return redisTemplate.opsForList().rightPushAll(listKey, result);
    }

    /**
     * 获取所有列表(默认从左边第一个开始)
     *
     * @param listKey
     * @return
     */
    public static <T> List<T> getListAll(String listKey, Class<T> clazz) {
        return getListByRange(listKey, clazz, 0, Integer.MAX_VALUE);
    }

    /**
     * 获取列表所有数据
     *
     * @param listKey
     * @return
     */
    public static <T> List<T> getListByRange(String listKey, Class<T> clazz, Integer offset, Integer limit) {
        List<T> result = Lists.newArrayList();
        List<String> list = getListRange(listKey, offset, limit);
        if (list != null) {
            for (String string : list) {
                result.add(deserialize(string, clazz));
            }
        }
        return result;
    }

    /**
     * 获取列表所有数据
     *
     * @param listKey
     * @return
     */
    public static <T> List<T> getList(String listKey, Class<T> clazz, Integer pageNo, Integer pageSize) {
        List<T> result = Lists.newArrayList();
        int[] offsetAndEnd = PageUtil.createOffsetAndEnd(pageNo, pageSize);
        List<String> list = getListRange(listKey, offsetAndEnd[0], offsetAndEnd[1]);
        if (list != null) {
            for (String string : list) {
                result.add(deserialize(string, clazz));
            }
        }
        return result;
    }

    /**
     * 获取list某一范围的段
     *
     * @param listKey
     * @param start
     * @param end
     * @return
     */
    public static List<String> getListRange(String listKey, int start, int end) {

        try {
            return redisTemplate.opsForList().range(listKey, start, end);
        } catch (Exception e) {
            handleJedisException(e);
        }
        return null;
    }

    /**
     * 获取Map结构所有数据(key为String)
     *
     * @param mapKey
     * @return
     */
    public static Map<String, String> getStringMapAll(String mapKey) {
        try {
            HashOperations<String, String, String> hashOpt = redisTemplate.opsForHash();
            Map<String, String> result = hashOpt.entries(mapKey);
            return result;
        } catch (Exception e) {
            handleJedisException(e);
        }
        return null;
    }

    public static <V> void putToMap(String mapKey, String field, V value) {
        putStringToMap(mapKey, field, serialize(value));
    }

    /**
     * 添加到Map结构(key为String)
     *
     * @param mapKey
     * @param field
     * @param value
     */
    public static void putStringToMap(String mapKey, String field, String value) {
        try {
            redisTemplate.opsForHash().put(mapKey, field, value);
        } catch (Exception e) {
            handleJedisException(e);
        }
    }
    
    public static void addStringToList(String key, String value) {
        try {
        	redisTemplate.opsForList().leftPush(key, value);
        } catch (Exception e) {
            handleJedisException(e);
        }
    }
    public static <V> void addToList(String mapKey, V value) {
    	addStringToList(mapKey, serialize(value));
    }

    /**
     * 添加到Map结构（key为String）返回 批量设置成功的记录数,失败返回-1
     *
     * @param mapKey
     * @param data
     */
    public static int putStringToMap(String mapKey, Map<String, String> data) {
        try {
            HashOperations<String, String, String> hashOpt = redisTemplate.opsForHash();
            hashOpt.putAll(mapKey, data);
        } catch (Exception e) {
            handleJedisException(e);
            return -1;
        }
        return data.size();
    }

    /**
     * 从Map结构中获取数据
     *
     * @param mapKey
     * @param field
     * @return
     */
    public static String getStringFromMap(String mapKey, String field) {
        try {
            HashOperations<String, String, String> hashOpt = redisTemplate.opsForHash();
            String data = hashOpt.get(mapKey, field);
            return data;
        } catch (Exception e) {
            handleJedisException(e);
        }
        return null;
    }

    /**
     * 判断Map结构是否存在field
     *
     * @param mapKey
     * @param field
     * @return
     */
    public static boolean hexistsString(String mapKey, String field) {
        try {
            HashOperations<String, String, String> hashOpt = redisTemplate.opsForHash();
            return hashOpt.hasKey(mapKey, field);
        } catch (Exception e) {
            handleJedisException(e);
        }
        return false;
    }

    /**
     * 判断Map结构是否存在field
     *
     * @param mapKey
     * @param field
     * @return
     */
    public static <F> boolean hexists(String mapKey, F field) {
        try {
            HashOperations<String, String, String> hashOpt = redisTemplate.opsForHash();
            return hashOpt.hasKey(mapKey, serialize(field));
        } catch (Exception e) {
            handleJedisException(e);
        }
        return false;
    }

    /**
     * 从Map结构中获取数据
     *
     * @param mapKey
     * @param field
     * @return
     */
    public static <F, V> V getFromMap(String mapKey, String field, Class<V> clazz) {
        return deserialize(getStringFromMap(mapKey, field), clazz);
    }

    /**
     * 从Map结构中获取数据
     *
     * @param mapKey
     * @param field
     * @return
     */
    public static <F> String getFromMap(String mapKey, F field) {
        try {
            HashOperations<String, String, String> hashOpt = redisTemplate.opsForHash();
            String data = hashOpt.get(mapKey, serialize(field));
            return data;
        } catch (Exception e) {
            handleJedisException(e);
        }
        return null;
    }

    /**
     * 从map中移除记录
     *
     * @param mapKey
     * @param field
     */
    public static <F> void removeFromMap(String mapKey, F field) {
        removeStringFromMap(mapKey, serialize(field));
    }

    /**
     * 从map中移除某个filed的记录
     *
     * @param mapKey
     * @param field
     * @return
     */
    public static long removeStringFromMap(String mapKey, String field) {
        try {
            HashOperations<String, String, String> hashOpt = redisTemplate.opsForHash();
            return hashOpt.delete(mapKey, field);
        } catch (Exception e) {
            handleJedisException(e);
        }
        return -1;
    }

    /**
     * map数据序列化转换
     *
     * @param data
     * @return
     */
    public static Map<String, String> serializeMap(Map<Object, Object> data) {
        Map<String, String> result = new HashMap<String, String>();
        try {
            Set<Object> keys = data.keySet();
            if (keys != null && keys.size() > 0) {
                for (Object key : keys) {
                    result.put(serialize(key), serialize(data.get(key)));
                }
            }
        } catch (Exception e) {
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T> T deserialize(String v, Class<T> type) {
        if (StringUtils.isBlank(v)) {
            return null;
        }
        return (T) JsonUtil.fromJson(v, TypeFactory.rawClass(type));
    }

    public static String serialize(Object t) {
        if (t == null) {
            return new String();
        }
        //return new Gson().toJson(t);
        return JsonUtil.toJson(t);
    }

    private static boolean handleJedisException(Exception jedisException) {
        jedisException.printStackTrace();
        if (jedisException instanceof JedisConnectionException) {
            LOGGER.error("redis connection lost.", jedisException);
        } else if (jedisException instanceof JedisDataException) {
            if ((jedisException.getMessage() != null) && (jedisException.getMessage().indexOf("READONLY") != -1)) {
                LOGGER.error("redis are read-only slave.", jedisException);
            } else {
                return false;
            }
        } else {
            LOGGER.error("Jedis exception happen.", jedisException);
        }
        return true;
    }

    public static Long increment(String key) {
		return incrementBy(key, 1);
	}
    
    public static Long incrementBy(String key, long delta) {
    	return redisTemplate.opsForValue().increment(key, delta);
    }
    
    public static Set<String> getKeys(String pattern) {
    	return redisTemplate.keys(pattern);
    }
}
