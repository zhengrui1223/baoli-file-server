package com.baoli.component.redis;

import com.baoli.util.LogExceptionStackTrace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/************************************************************
 * @Description:
 * @Author: zhengrui
 * @Date 2018-05-29 20:15
 ************************************************************/

@Component
public class RedisCacheManager {

    private static final Logger logger = LoggerFactory.getLogger(RedisCacheManager.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param expireTime 时间(秒)
     * @return 设置是否成功
     */
    public Boolean expire(String key, long expireTime) {
        Long start = System.currentTimeMillis();
        try {
            Boolean success = redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：expire key:{} execution time:{}ms", key, time);
            }

            return success;
        } catch (Exception e) {
            logger.error("command：expire key:{} ex={}", key, LogExceptionStackTrace.errorStackTrace(e));
            return null;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效 null服务器连接不上
     */
    public Long getExpire(String key) {
        Long start = System.currentTimeMillis();
        try {
            Long expire = redisTemplate.getExpire(key, TimeUnit.SECONDS);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：getExpire key:{} execution time:{}ms", key, time);
            }

            return expire;
        } catch (Exception e) {
            logger.error("command：getExpire key:{} ex={}", key, LogExceptionStackTrace.errorStackTrace(e));
            return null;
        }
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在 null服务器连接不上
     */
    public Boolean exists(String key) {
        Long start = System.currentTimeMillis();
        try {
            Boolean exist = redisTemplate.hasKey(key);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：exists key:{} execution time:{}ms", key, time);
            }

            return exist;
        } catch (Exception e) {
            logger.error("command：exists key:{} ex={}", key, LogExceptionStackTrace.errorStackTrace(e));
            return null;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    public void del(String... key) {
        try {
            if (key != null && key.length > 0) {
                Long start = System.currentTimeMillis();
                if (key.length == 1) {
                    redisTemplate.delete(key[0]);
                } else {
                    redisTemplate.delete(CollectionUtils.arrayToList(key));
                }

                Long end = System.currentTimeMillis();
                Long time = end - start;
                if (time > 500L) {
                    logger.warn("command：del key:{} execution time:{}ms", key, time);
                }
            }
        } catch (Exception e) {
            logger.error("command：del key:{} ex={}", key, LogExceptionStackTrace.errorStackTrace(e));
        }
    }

    // ============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        Long start = System.currentTimeMillis();
        try {
            Object obj = redisTemplate.opsForValue().get(key);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：get key:{} execution time:{}ms", key, time);
            }

            return obj;
        } catch (Exception e) {
            logger.error("command：get key:{} ex={}", key, LogExceptionStackTrace.errorStackTrace(e));
            return null;
        }
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public Boolean set(String key, String value) {
        Long start = System.currentTimeMillis();
        try {
            redisTemplate.opsForValue().set(key, value);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：set key:{} execution time:{}ms", key, time);
            }

            return true;
        } catch (Exception e) {
            logger.error("command：set key:{} ex={}", key, LogExceptionStackTrace.errorStackTrace(e));
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param expireTime  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public Boolean set(String key, String value, long expireTime) {
        Long start = System.currentTimeMillis();
        try {
            if (expireTime > 0) {
                redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：set key:{} execution time:{}ms", key, time);
            }

            return true;
        } catch (Exception e) {
            logger.error("command：set key:{} ex={}", key, LogExceptionStackTrace.errorStackTrace(e));
            return false;
        }
    }

    /**
     * 递增
     *
     * @param key 键
     * @param by  要增加几(大于0)
     * @return
     */
    public Long incr(String key, long delta) {
        Long start = System.currentTimeMillis();
        try {
            Long increment = redisTemplate.opsForValue().increment(key, delta);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：incr key:{} execution time:{}ms", key, time);
            }

            return increment;
        } catch (Exception e) {
            logger.error("command：incr key:{} ex={}", key, LogExceptionStackTrace.errorStackTrace(e));
            return null;
        }
    }

    /**
     * 递减
     *
     * @param key 键
     * @param by  要减少几(小于0)
     * @return
     */
    public Long decr(String key, long delta) {
        Long start = System.currentTimeMillis();
        try {
            Long increment = redisTemplate.opsForValue().increment(key, -delta);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：decr key:{} execution time:{}ms", key, time);
            }

            return increment;
        } catch (Exception e) {
            logger.error("command：decr key:{} ex={}", key, LogExceptionStackTrace.errorStackTrace(e));
            return null;
        }
    }

    // ================================Map=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hget(String key, String item) {
        Long start = System.currentTimeMillis();
        try {
            Object obj = redisTemplate.opsForHash().get(key, item);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：hget key:{} execution time:{}ms", key, time);
            }

            return obj;
        } catch (Exception e) {
            logger.error("command：hget key:{} ex={}", key, LogExceptionStackTrace.errorStackTrace(e));
            return null;
        }
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
        Long start = System.currentTimeMillis();
        try {
            Map entries = redisTemplate.opsForHash().entries(key);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：hmget key:{} execution time:{}ms", key, time);
            }

            return entries;
        } catch (Exception e) {
            logger.error("command：hmget key:{} ex={}", key, LogExceptionStackTrace.errorStackTrace(e));
            return null;
        }
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmset(String key, Map<String, Object> map) {
        Long start = System.currentTimeMillis();
        try {
            redisTemplate.opsForHash().putAll(key, map);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：hmset key:{} execution time:{}ms", key, time);
            }

            return true;
        } catch (Exception e) {
            logger.error("command：hmset key:{} ex={}", key, LogExceptionStackTrace.errorStackTrace(e));
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param expireTime 时间(秒)
     * @return true成功 false失败
     */
    public Boolean hmset(String key, Map<String, Object> map, long expireTime) {
        Long start = System.currentTimeMillis();
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (expireTime > 0) {
                expire(key, expireTime);
            }
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：hmset key:{} execution time:{}ms", key, time);
            }

            return true;
        } catch (Exception e) {
            logger.error("command：hmset key:{} ex={}", key, LogExceptionStackTrace.errorStackTrace(e));
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public Boolean hset(String key, String item, Object value) {
        Long start = System.currentTimeMillis();
        try {
            redisTemplate.opsForHash().put(key, item, value);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：hset key:{} execution time:{}ms", key, time);
            }

            return true;
        } catch (Exception e) {
            logger.error("command：hset key:{} ex={}", key, LogExceptionStackTrace.errorStackTrace(e));
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param expireTime  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public Boolean hset(String key, String item, Object value, long expireTime) {
        Long start = System.currentTimeMillis();
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (expireTime > 0) {
                expire(key, expireTime);
            }
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：hset key:{} execution time:{}ms", key, time);
            }

            return true;
        } catch (Exception e) {
            logger.error("command：hset key:{} ex={}", key, LogExceptionStackTrace.errorStackTrace(e));
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hdel(String key, Object... item) {
        Long start = System.currentTimeMillis();
        try {
            redisTemplate.opsForHash().delete(key, item);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：hdel key:{} execution time:{}ms", key, time);
            }

        } catch (Exception e) {
            logger.error("command：hdel key:{} ex={}", key, LogExceptionStackTrace.errorStackTrace(e));
        }
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public Boolean hexists(String key, String item) {
        Long start = System.currentTimeMillis();
        try {
            Boolean exist = redisTemplate.opsForHash().hasKey(key, item);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：hexists key:{} execution time:{}ms", key, time);
            }

            return exist;
        } catch (Exception e) {
            logger.error("command：hexists key:{} ex={}", key, LogExceptionStackTrace.errorStackTrace(e));
            return null;
        }
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    public Double hincr(String key, String item, double by) {
        Long start = System.currentTimeMillis();
        try {
            Double increment = redisTemplate.opsForHash().increment(key, item, by);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：hincr key:{} execution time:{}ms", key, time);
            }

            return increment;
        } catch (Exception e) {
            logger.error("command：hincr key:{} ex={}", key, LogExceptionStackTrace.errorStackTrace(e));
            return null;
        }
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    public Double hdecr(String key, String item, double by) {
        Long start = System.currentTimeMillis();
        try {
            Double increment = redisTemplate.opsForHash().increment(key, item, -by);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：hdecr key:{} execution time:{}ms", key, time);
            }

            return increment;
        } catch (Exception e) {
            logger.error("command：hdecr key:{} ex={}", key, LogExceptionStackTrace.errorStackTrace(e));
            return null;
        }
    }

    // ============================set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public Set<Object> smembers(String key) {
        Long start = System.currentTimeMillis();
        try {
            Set members = redisTemplate.opsForSet().members(key);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：smembers key:{} execution time:{}ms", key, time);
            }

            return members;
        } catch (Exception e) {
            logger.error("command：smembers key:{} ex={}", key, LogExceptionStackTrace.errorStackTrace(e));
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public Boolean sismember(String key, Object value) {
        Long start = System.currentTimeMillis();
        try {
            Boolean exist = redisTemplate.opsForSet().isMember(key, value);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：sismember key:{} execution time:{}ms", key, time);
            }

            return exist;
        } catch (Exception e) {
            logger.error("command：sismember key:{} ex={}", key, LogExceptionStackTrace.errorStackTrace(e));
            return null;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Long sadd(String key, String... values) {
        Long start = System.currentTimeMillis();
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：sadd key:{} execution time:{}ms", key, time);
            }

            return count;
        } catch (Exception e) {
            logger.error("command：sadd key:{} ex={}", key, LogExceptionStackTrace.errorStackTrace(e));
            return null;
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param expireTime   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Long sadd(String key, long expireTime, String... values) {
        Long start = System.currentTimeMillis();
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (expireTime > 0)
                expire(key, expireTime);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：sadd key:{} execution time:{}ms", key, time);
            }

            return count;
        } catch (Exception e) {
            logger.error("command：sadd key:{} ex={}", key, LogExceptionStackTrace.errorStackTrace(e));
            return null;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public Long scard(String key) {
        Long start = System.currentTimeMillis();
        try {
            Long size = redisTemplate.opsForSet().size(key);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：scard key:{} execution time:{}ms", key, time);
            }

            return size;
        } catch (Exception e) {
            logger.error("command：scard key:{} ex={}", key, LogExceptionStackTrace.errorStackTrace(e));
            return null;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public Long sremove(String key, Object... values) {
        Long start = System.currentTimeMillis();
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：sremove key:{} execution time:{}ms", key, time);
            }

            return count;
        } catch (Exception e) {
            logger.error("command：sremove key:{} ex={}", key, LogExceptionStackTrace.errorStackTrace(e));
            return null;
        }
    }
    // ===============================list=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return
     */
    public List<Object> lrange(String key, long startIndex, long endIndex) {
        Long start = System.currentTimeMillis();
        try {
            List list = redisTemplate.opsForList().range(key, startIndex, endIndex);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：lrange key:{} execution time:{}ms", key, time);
            }

            return list;
        } catch (Exception e) {
            logger.error("command：lrange key:{} ex={}", key, LogExceptionStackTrace.errorStackTrace(e));
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public Long llen(String key) {
        Long start = System.currentTimeMillis();
        try {
            Long size = redisTemplate.opsForList().size(key);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：llen key:{} execution time:{}ms", key, time);
            }

            return size;
        } catch (Exception e) {
            logger.error("command：llen key:{} ex={}", key, LogExceptionStackTrace.errorStackTrace(e));
            return null;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object lindex(String key, long index) {
        Long start = System.currentTimeMillis();
        try {
            Object obj = redisTemplate.opsForList().index(key, index);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：lindex key:{} execution time:{}ms", key, time);
            }

            return obj;
        } catch (Exception e) {
            logger.error("command：lindex key:{} ex={}", key, LogExceptionStackTrace.errorStackTrace(e));
            return null;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public Boolean lset(String key, String value) {
        Long start = System.currentTimeMillis();
        try {
            redisTemplate.opsForList().rightPush(key, value);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：lset key:{} execution time:{}ms", key, time);
            }

            return true;
        } catch (Exception e) {
            logger.error("command：lset key:{} ex={}", key, LogExceptionStackTrace.errorStackTrace(e));
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param expireTime  时间(秒)
     * @return
     */
    public Boolean lset(String key, String value, long expireTime) {
        Long start = System.currentTimeMillis();
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (expireTime > 0)
                expire(key, expireTime);

            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：lset key:{} execution time:{}ms", key, time);
            }

            return true;
        } catch (Exception e) {
            logger.error("command：lset key:{} ex={}", key, LogExceptionStackTrace.errorStackTrace(e));
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lset(String key, List<String> value) {
        Long start = System.currentTimeMillis();
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：lset key:{} execution time:{}ms", key, time);
            }

            return true;
        } catch (Exception e) {
            logger.error("command：lset key:{} ex={}", key, LogExceptionStackTrace.errorStackTrace(e));
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param expireTime  时间(秒)
     * @return
     */
    public Boolean lset(String key, List<String> value, long expireTime) {
        Long start = System.currentTimeMillis();
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (expireTime > 0)
                expire(key, expireTime);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：lset key:{} execution time:{}ms", key, time);
            }

            return true;
        } catch (Exception e) {
            logger.error("command：lset key:{} ex={}", key, LogExceptionStackTrace.errorStackTrace(e));
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public Boolean lset(String key, long index, String value) {
        Long start = System.currentTimeMillis();
        try {
            redisTemplate.opsForList().set(key, index, value);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：lset key:{} execution time:{}ms", key, time);
            }

            return true;
        } catch (Exception e) {
            logger.error("command：lset key:{} ex={}", key, LogExceptionStackTrace.errorStackTrace(e));
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public Long lremove(String key, long count, String value) {
        Long start = System.currentTimeMillis();
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：lremove key:{} execution time:{}ms", key, time);
            }

            return remove;
        } catch (Exception e) {
            logger.error("command：lremove key:{} ex={}", key, LogExceptionStackTrace.errorStackTrace(e));
            return null;
        }
    }

    // ===============================zset=================================

    /**
     * sortedSet
     *
     * @param key
     * @param value
     * @param score
     * @return
     */
    public Boolean zadd(String key, String value, Double score) {
        Long start = System.currentTimeMillis();

        try {
            Boolean success = redisTemplate.opsForZSet().add(key, value, score);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：zadd key:{} execution time:{}ms", key, time);
            }

            return success;
        } catch (Exception e) {
            logger.error("command：zadd key:{} ex={}", key, LogExceptionStackTrace.errorStackTrace(e));
            return false;
        }
    }
}
