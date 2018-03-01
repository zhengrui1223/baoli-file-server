package com.baoli.webservice.service;

import com.baoli.webservice.bean.WebServiceCommonResponse;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

/************************************************************
 * @Description:
 * @Author: jerry.zheng
 * @Date Created in 14:48 2018\2\28 0028        
 ************************************************************/

@WebService
public class TestWebService2 {

    @WebMethod
    public WebServiceCommonResponse testHello(@XmlElement(name = "message", required = true) String message) {
        WebServiceCommonResponse commonResponse = new WebServiceCommonResponse();
        commonResponse.setMessage(message);
        commonResponse.setType("S");
        return commonResponse;
    }
}
