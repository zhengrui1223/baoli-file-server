package com.baoli.util;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/************************************************************
 * @author jerry.zheng
 * @Description
 * @date 2017-12-07 13:54
 ************************************************************/

public class WebServiceSSLClient {
    public static CloseableHttpClient createSSLClientDefault() {
        CloseableHttpClient httpClient = null;
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                // 信任所有
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslFactory = new SSLConnectionSocketFactory(
                    sslContext);
            httpClient = HttpClients.custom().setSSLSocketFactory(sslFactory).build();
        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
            e.printStackTrace();
        }
        return httpClient;
    }
}

