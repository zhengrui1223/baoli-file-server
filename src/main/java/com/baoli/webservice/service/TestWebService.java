package com.baoli.webservice.service;

import com.baoli.webservice.bean.WebServiceCommonResponse;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

/************************************************************
 * @Description: TODO
 * @Author: jerry.zheng
 * @Date Created in 14:48 2018\2\28 0028        
 ************************************************************/

/**
 *  <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.webservice.baoli.com/">
         <soapenv:Header>
            <username>admin</username>
            <password>pass</password>
         <soapenv:Header/>
         <soapenv:Body>
            <ser:testHello>
                <message>fewfwefw</message>
            </ser:testHello>
         </soapenv:Body>
    </soapenv:Envelope>

    加入了认证， 需要在header标签中注入用户名密码， 但是如何在http header中认证呢？
 */
@WebService
public class TestWebService {

    @WebMethod
    public WebServiceCommonResponse testHello(@XmlElement(name = "message", required = true) String message) {
        WebServiceCommonResponse commonResponse = new WebServiceCommonResponse();
        commonResponse.setMessage(message);
        commonResponse.setType("S");
        return commonResponse;
    }
}
