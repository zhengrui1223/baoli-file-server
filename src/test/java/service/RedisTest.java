package service;

import redis.clients.jedis.Jedis;

/************************************************************
 * @Description:
 * @Author: zhengrui
 * @Date 2018-05-29 16:08
 ************************************************************/

public class RedisTest {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.137.127", 6390);
        //权限认证
        //jedis.auth("jdd.com");
        System.out.println("连接成功");
        //设置 redis 字符串数据
        jedis.set("hello", "world");
        // 获取存储的数据并输出
        System.out.println("redis 存储的字符串为: " + jedis.get("hello"));
    }

}
