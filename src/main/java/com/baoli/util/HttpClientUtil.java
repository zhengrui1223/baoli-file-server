package com.baoli.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

/************************************************************
 * @author jerry.zheng
 * @Description
 * @date 2017-12-07 13:54
 ************************************************************/

public class HttpClientUtil {

    protected static Log log = LogFactory.getLog(HttpClientUtil.class);

    public static Map<String, String> restfulRequest(String url, String request, String timeOutSecond) {
        return HttpClientUtil.sendRequest(url, null, null, request, timeOutSecond, null, null, true);
    }

    public static Map<String, String> restfulRequest(String url, String userName, String passWord, String request, String timeOutSecond) {
        return HttpClientUtil.sendRequest(url, userName, passWord, request, timeOutSecond, null, null, true);
    }

    public static Map<String, String> webServiceRequest(String url, String request, String timeOutSecond) {
        return HttpClientUtil.sendRequest(url, null, null, request, timeOutSecond, null, null, false);
    }

    public static Map<String, String> webServiceRequest(String url, String userName, String passWord, String request, String timeOutSecond) {
        return HttpClientUtil.sendRequest(url, userName, passWord, request, timeOutSecond, null, null, false);
    }

    /**
     *
     * @param url           请求路径
     * @param userName      Basic认证用户名
     * @param passWord      Basic认证密码
     * @param request       请求内容（可以为基于SOAP的xml或者Restful的json）
     * @param timeOutSecond 超时时间
     * @param proxyHost     代理服务器地址
     * @param proxyPort     代理服务器端口
     * @param isRestful     是restful还是soap
     * @return
     */
    public static Map<String, String> sendRequest(String url, String userName, String passWord, String request,
                                                  String timeOutSecond, String proxyHost, Integer proxyPort, boolean isRestful) {
        Map<String, String> resultMap = new HashMap<>();
        // 返回字符串
        String responseXml = null;
        // 返回状态
        String responseStatusCode = "";
        // 创建参数队列
        CloseableHttpClient httpClient = null;
        //返回异常信息：
        String exceptionType = null;
        try {
            if ("https".equalsIgnoreCase(url.substring(0, 5))) {
                httpClient = WebServiceSSLClient.createSSLClientDefault();
            } else {
                httpClient = HttpClients.createDefault();
            }

            // 创建HttpPost
            HttpPost httpPost = new HttpPost(url);

            //请求内容设置编码
            HttpEntity re = new StringEntity(request, "UTF-8");

            //设置请求头
            httpPost.setHeader("Content-Type", "text/xml;charset=UTF-8");
            if (isRestful) {
                httpPost.setHeader("Content-Type", "application/json; charset=utf-8");
            }

            //判断是否需要Basic认证
            if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(passWord)) {
                httpPost.setHeader("Authorization", "Basic " + getBase64Code(userName, passWord));
            }

            //添加请求到请求体中
            httpPost.setEntity(re);

            //若无超时时间， 则设置默认的
            Integer timeOut;
            try {
                timeOut = Float.valueOf(new Float(timeOutSecond) * 1000).intValue();
            } catch (Exception e) {
                timeOut = 2 * 60 * 1000;
            }

            //设置请求和传输超时时间
            RequestConfig timeOutConfig = RequestConfig.custom().setSocketTimeout(timeOut).setConnectTimeout(timeOut).build();
            httpPost.setConfig(timeOutConfig);

            //判断是否需要代理
            if (StringUtils.isNotEmpty(proxyHost) && proxyPort != null) {
                HttpHost proxy = new HttpHost(proxyHost, proxyPort);
                RequestConfig config = RequestConfig.custom().setProxy(proxy).setSocketTimeout(timeOut).setConnectTimeout(timeOut).build();
                httpPost.setConfig(config);
            }

            CloseableHttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                responseStatusCode = response.getStatusLine() != null ? String.valueOf(response.getStatusLine().getStatusCode()) : "";
                try {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        responseXml = EntityUtils.toString(entity, "UTF-8");
                    }
                } finally {
                    response.close();
                    httpPost.abort();
                }
            }
        } catch (ConnectTimeoutException | SocketTimeoutException e1) {
            log.error(e1.getMessage());
            exceptionType = "TimeoutException";
        } catch (Exception e) {
            log.error(e.getMessage());
            exceptionType = "Exception";
        } finally {
            // 关闭连接,释放资源
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                exceptionType = "Exception";
            }
        }
        resultMap.put(Context.RESPONSE_STATUS_CODE, responseStatusCode);
        resultMap.put(Context.RESPONSE_BODY, responseXml);
        resultMap.put(Context.RESPONSE_EXCEPTION_TYPE, exceptionType);
        return resultMap;
    }

    public static String getBase64Code(String userName, String passWord) {
        Base64 token = new Base64();
        return token.encodeAsString(new String(userName + ":" + passWord).getBytes());
    }

}
