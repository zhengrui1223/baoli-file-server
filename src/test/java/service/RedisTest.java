package service;

import redis.clients.jedis.Jedis;

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
        System.out.println("连接成功");
        //设置 redis 字符串数据
        jedis.set("hello", "world1111111111111111111111111111");
        // 获取存储的数据并输出
        System.out.println("redis 存储的字符串为: " + jedis.get("hello"));
    }

}
