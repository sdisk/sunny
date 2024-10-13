package com.xiaoyu.sunny.service.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description: Redis 操作工具
 * @Author XiaoYu
 * @Date 2024/9/13 13:40
 */
@Component
public class RedisOperator {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ValueOperations<String, String> valueOperator;
    @Autowired
    private HashOperations<String, String, Object> hashOperator;
    @Autowired
    private ListOperations<String, Object> listOperator;
    @Autowired
    private SetOperations<String, Object> setOperator;
    @Autowired
    private ZSetOperations<String, Object> zSetOperator;

    /**
     * 默认过期时长，单位：秒
     */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24;

    /** 不设置过期时长 */
    public final static long NOT_EXPIRE = -1;

    /**
     * Redis的根操作路径
     */
    @Value("${redis.root:sunny}")
    private String category;

    public RedisOperator setCategory(String category) {
        this.category = category;
        return this;
    }

    /**
     * 获取Key的全路径
     *
     * @param key key
     * @return full key
     */
    public String getFullKey(String key) {
        return this.category + ":" + key;
    }


    //
    // key
    // ------------------------------------------------------------------------------
    /**
     * 判断key是否存在
     *
     * <p>
     * <i>exists key</i>
     *
     * @param key key
     */
    public boolean existsKey(String key) {
        return redisTemplate.hasKey(getFullKey(key));
    }


    /**
     * 重命名key. 如果newKey已经存在，则newKey的原值被覆盖
     *
     * <p>
     * <i>rename oldKey newKey</i>
     *
     * @param oldKey oldKeys
     * @param newKey newKey
     */
    public void renameKey(String oldKey, String newKey){
        redisTemplate.rename(getFullKey(oldKey), getFullKey(newKey));
    }

    /**
     * newKey不存在时才重命名.
     *
     * <p>
     * <i>renamenx oldKey newKey</i>
     *
     * @param oldKey oldKey
     * @param newKey newKey
     * @return 修改成功返回true
     */
    public boolean renameKeyNx(String oldKey, String newKey){
        return redisTemplate.renameIfAbsent(getFullKey(oldKey), getFullKey(newKey));
    }

    /**
     * 删除key
     *
     * <p>
     * <i>del key</i>
     *
     * @param key key
     */
    public void deleteKey(String key){
        redisTemplate.delete(key);
    }

    /**
     * 删除key
     *
     * <p>
     * <i>del key1 key2 ...</i>
     *
     * @param keys 可传入多个key
     */
    public void deleteKey(String ... keys){
        Set<String> ks = Stream.of(keys).map(k -> getFullKey(k)).collect(Collectors.toSet());
        redisTemplate.delete(ks);
    }

    /**
     * 删除key
     *
     * <p>
     * <i>del key1 key2 ...</i>
     *
     * @param keys key集合
     */
    public void deleteKey(Collection<String> keys){
        Set<String> ks = keys.stream().map(k -> getFullKey(k)).collect(Collectors.toSet());
        redisTemplate.delete(ks);
    }

    /**
     * 设置key的生命周期，单位秒
     *
     * <p>
     * <i>expire key seconds</i><br>
     * <i>pexpire key milliseconds</i>
     *
     * @param key key
     * @param time 时间数
     * @param timeUnit TimeUnit 时间单位
     */
    public void expireKey(String key, long time, TimeUnit timeUnit){
        redisTemplate.expire(key, time, timeUnit);
    }

    /**
     * 设置key在指定的日期过期
     *
     * <p>
     * <i>expireat key timestamp</i>
     *
     * @param key key
     * @param date 指定日期
     */
    public void expireKeyAt(String key, Date date){
        redisTemplate.expireAt(key, date);
    }

    /**
     * 查询key的生命周期
     *
     * <p>
     * <i>ttl key</i>
     *
     * @param key key
     * @param timeUnit TimeUnit 时间单位
     * @return 指定时间单位的时间数
     */
    public long getKeyExpire(String key, TimeUnit timeUnit){
        return redisTemplate.getExpire(key, timeUnit);
    }

    /**
     * 将key设置为永久有效
     *
     * <p>
     * <i>persist key</i>
     *
     * @param key key
     */
    public void persistKey(String key){
        redisTemplate.persist(key);
    }


    /**
     *
     * @return RedisTemplate
     */
    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    /**
     *
     * @return ValueOperations
     */
    public ValueOperations<String, String> getValueOperator() {
        return valueOperator;
    }

    /**
     *
     * @return HashOperations
     */
    public HashOperations<String, String, Object> getHashOperator() {
        return hashOperator;
    }

    /**
     *
     * @return ListOperations
     */
    public ListOperations<String, Object> getListOperator() {
        return listOperator;
    }

    /**
     *
     * @return SetOperations
     */
    public SetOperations<String, Object> getSetOperator() {
        return setOperator;
    }

    /**
     *
     * @return ZSetOperations
     */
    public ZSetOperations<String, Object> getZSetOperator() {
        return zSetOperator;
    }
}
