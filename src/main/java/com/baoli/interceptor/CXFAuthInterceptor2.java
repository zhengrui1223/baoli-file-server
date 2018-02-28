package com.baoli.interceptor;

import org.apache.commons.codec.binary.Base64;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.soap.SOAPException;
import java.util.List;
import java.util.Map;

/************************************************************
 * @Description: Basic认证
 * @Author: jerry.zheng
 * @Date Created in 15:05 2018\2\28 0028        
 ************************************************************/

@SuppressWarnings("Duplicates")
public class CXFAuthInterceptor2 extends AbstractPhaseInterceptor<SoapMessage> {
    private static final Logger logger = LoggerFactory.getLogger(CXFAuthInterceptor2.class);
    private SAAJInInterceptor saa = new SAAJInInterceptor();

    private static final String USER_NAME = "admin";
    private static final String USER_PASSWORD = "pass";

    public CXFAuthInterceptor2() {
        super(Phase.PRE_PROTOCOL);
        getAfter().add(SAAJInInterceptor.class.getName());
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {

        String baseAuth = null;
        Map<String, List<String>> reqHeaders = CastUtils.cast((Map<?, ?
                >) message.get(Message.PROTOCOL_HEADERS));
        if (reqHeaders != null) {
            for (Map.Entry<String, List<String>> e : reqHeaders.entrySet()) {
                if ("Authorization".equalsIgnoreCase(e.getKey()))
                    baseAuth = e.getValue().get(0);
            }
        }

        String userName = "";
        String passWord = "";

        if ((baseAuth != null) && baseAuth.startsWith("Basic ")) {
            byte[] base64Token;
            try {
                base64Token = baseAuth.substring(6).getBytes("UTF-8");
                String token = new String(Base64.decodeBase64(base64Token), "UTF-8");

                int delim = token.indexOf(":");
                if (delim != -1) {
                    userName = token.substring(0, delim);
                    passWord = token.substring(delim + 1);
                }
                System.out.printf("用户名：%s\n密 码：%s\n", userName, passWord);
            } catch (Exception e) {
                throw new Fault(e);
            }
        }

        if (USER_NAME.equals(userName) && USER_PASSWORD.equals(passWord)) {
            logger.debug("admin auth success");
        } else {
            SOAPException soapExc = new SOAPException("认证错误");
            logger.debug("admin auth failed");
            throw new Fault(soapExc);
        }
    }
}
