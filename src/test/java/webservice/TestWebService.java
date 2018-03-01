package webservice;

import com.baoli.util.Context;
import com.baoli.util.HttpClientUtil;
import com.baoli.webservice.bean.WebServiceCommonResponse;
import com.baoli.webservice.client.BaseClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
import org.junit.Test;

import java.util.Map;

/************************************************************
 * @Description: TODO
 * @Author: jerry.zheng
 * @Date Created in 15:16 2018\2\28 0028        
 ************************************************************/


public class TestWebService extends BaseClient{

    private static final String USER_NAME = "admin";
    private static final String PASS_WORD = "pass";

    @Test
    public void testSoapHeaderAuth() {
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://localhost:8080/services/test1?wsdl");

        //需要密码的情况需要加上用户名和密码
        client.getOutInterceptors().add(new CXFClientLoginInterceptor(USER_NAME, PASS_WORD));

        try {
            //invoke("方法名", 参数1,参数2,参数3....);
            Object[] objects = client.invoke("testHello", "1111111111111111111111111");
            System.out.println("返回数据:" + objects[0]);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBasicAuth() {
        Document document = DocumentHelper.createDocument();
        Element rootElement = document.addElement("soapenv:Envelope");
        rootElement.addAttribute("xmlns:soapenv", "http://schemas.xmlsoap.org/soap/envelope/");
        rootElement.addAttribute("xmlns:ser", "http://service.webservice.baoli.com/");
        DOMElement header = new DOMElement("soapenv:Header");
        rootElement.add(header);
        DOMElement body = new DOMElement("soapenv:Body");
        DOMElement subBody = new DOMElement("ser:testHello");
        DOMElement message = new DOMElement("message");
        message.setText("test webservice");
        subBody.add(message);
        body.add(subBody);
        rootElement.add(body);

        Map<String, String> response = HttpClientUtil.webServiceRequest(
                "http://localhost:8080/services/test2",
                "admin",
                "pass",
                document.asXML(), "10");

        WebServiceCommonResponse commonResponse = new WebServiceCommonResponse();
        //TimeoutException
        if (StringUtils.isNotBlank(response.get(Context.RESPONSE_EXCEPTION_TYPE)) && "TimeoutException".equals(response.get(Context.RESPONSE_EXCEPTION_TYPE))){
            commonResponse.setMessage("连接系统超时，请重试，或联系管理员！");
        }
        commonResponse = convertResponse(response, commonResponse, "<return>", "</return>");

        System.out.println(commonResponse.getMessage());
    }

}
