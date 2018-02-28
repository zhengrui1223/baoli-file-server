package com.baoli.webservice.client;

import com.baoli.util.Context;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.Map;

/************************************************************
 * @author jerry.zheng
 * @Description
 * @date 2017-12-08 17:25
 ************************************************************/

public abstract class BaseClient {

    protected Log log = LogFactory.getLog(getClass());

    protected <T> T convertResponse(Map<String, String> response, T entity, String returnStart, String returnEnd) {
        if (response != null && response.size() > 0) {
            if (response.get(Context.RESPONSE_STATUS_CODE) != null) {
                log.info(String.format("Service response status code is %s", response.get(Context.RESPONSE_STATUS_CODE)));
            }
            if (response.get(Context.RESPONSE_EXCEPTION_TYPE) != null) {
                log.info(String.format("Service response exception type is %s", response.get(Context.RESPONSE_EXCEPTION_TYPE)));
            }

            String responseStr = response.get(Context.RESPONSE_BODY);
            log.info("response:" + responseStr);
            if (StringUtils.isNotEmpty(responseStr)) {
                if (responseStr.contains(returnStart) && responseStr.contains(returnEnd)) {
                    String returnObj = responseStr.substring(responseStr.indexOf(returnStart), responseStr.indexOf(returnEnd) + returnStart.length() + 1);
                    try {
                        JAXBContext jaxbContext = JAXBContext.newInstance(entity.getClass());
                        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                        return (T) unmarshaller.unmarshal(new StringReader(returnObj));
                    } catch (JAXBException e) {
                        log.error(e.getMessage());
                    }
                }
            }
        }
        return entity;
    }

}
