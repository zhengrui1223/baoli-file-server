package com.baoli.util;

import org.springframework.stereotype.Component;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/************************************************************
 * @Description:
 * @Author: zhengrui
 * @Date 2018-06-09 17:17
 ************************************************************/

@Component
public class JedisClusterUtil {
    private JedisCluster jedis = null;

    public JedisClusterUtil() {
        Set<HostAndPort> nodes = new HashSet<HostAndPort>();
        nodes.add(new HostAndPort("192.168.137.127", 6390));
        nodes.add(new HostAndPort("192.168.137.127", 6391));
        nodes.add(new HostAndPort("192.168.137.135", 6390));
        nodes.add(new HostAndPort("192.168.137.135", 6391));
        nodes.add(new HostAndPort("192.168.137.150", 6391));
        nodes.add(new HostAndPort("192.168.137.150", 6392));
        jedis = new JedisCluster(nodes);
    }

    public JedisCluster getJedisCluster() {
        return jedis;
    }
}
