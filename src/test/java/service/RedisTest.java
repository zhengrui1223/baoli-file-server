package service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.*;

/************************************************************
 * @Description:
 * @Author: zhengrui
 * @Date 2018-05-29 16:08
 ************************************************************/

public class RedisTest {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("47.98.147.110", 6379);
        //权限认证
        jedis.auth("Jiao1223Yu!");

        generatorSortedSet(jedis);

        Long count = jedis.zcount("sortedSet", 93d, 100d);
        System.out.println("count: " + count);
        int start = (count.intValue() / 10 - 1) * 10 + count.intValue() % 10;
        System.out.println("start: " + start);
        Set<String> testLimit = jedis.zrevrangeByScore("sortedSet", 100d, 93d, start < 0 ? 0 : start, 10);
        System.out.println(testLimit);
    }

    private static void generatorSortedSet(Jedis jedis) {
        Date now = new Date();
        Map<String, Double> map = new HashMap<>();
        for (int i=0; i< 100; i++) {
            //map.put("test" + i, now.getTime() / 1000 + i + 0d);
            map.put("test" + i, i + 0d);
        }

        jedis.zadd("sortedSet", map);

    }

}
