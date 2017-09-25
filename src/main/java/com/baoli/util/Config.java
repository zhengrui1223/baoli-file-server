package com.baoli.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/************************************************************
 * @author jerry.zheng
 * @Description
 * @date 2017-09-21 15:10
 ************************************************************/

@Component
public class Config {

    @Value("${fdfs.soTimeout}")
    private Integer fastDfsSoTimeout;

    @Value("${fdfs.connectTimeout}")
    private Integer fastDfsConnectTimeout;

    @Value("${fdfs.thumbImage.width}")
    private Integer fastDfsThumbImageWidth;

    @Value("${fdfs.thumbImage.height}")
    private Integer fastDfsThumbImageHeight;

    @Value("${fdfs.trackerList[0]}")
    private String fastDfsTrackerList0;

    @Value("${fdfs.nginx.protocol}")
    private String fastDfsNginxHosProtocol;

    @Value("${fdfs.nginx.host}")
    private String fastDfsNginxHost;

    @Value("${fdfs.nginx.port}")
    private String fastDfsNginxPort;

    @Value("${profile.filter.ignore.url}")
    private String ignoreUrl;

    public Integer getFastDfsSoTimeout() {
        return fastDfsSoTimeout;
    }

    public Integer getFastDfsConnectTimeout() {
        return fastDfsConnectTimeout;
    }

    public Integer getFastDfsThumbImageWidth() {
        return fastDfsThumbImageWidth;
    }

    public Integer getFastDfsThumbImageHeight() {
        return fastDfsThumbImageHeight;
    }

    public String getFastDfsTrackerList0() {
        return fastDfsTrackerList0;
    }

    public String getFastDfsNginxHost() {
        return fastDfsNginxHost;
    }

    public String getFastDfsNginxPort() {
        return fastDfsNginxPort;
    }

    public String getFastDfsNginxHosProtocol() {
        return fastDfsNginxHosProtocol;
    }

    public String getIgnoreUrl() {
        return ignoreUrl;
    }
}
