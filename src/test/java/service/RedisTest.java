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
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        //权限认证
        jedis.auth("Jiao1223Yu!");


        Map<String, Double> map = new HashMap<>();
        map.put("a", 1d);
        map.put("a1", 1d);
        map.put("a2", 1d);
        map.put("b", 2d);
        map.put("c", 3d);
        map.put("d", 4d);

        jedis.zadd("testLimit", map);

        Set<String> testLimit = jedis.zrevrangeByScore("testLimit", 4d, 1d, 0, 4);
        System.out.println(testLimit);


    }

}
