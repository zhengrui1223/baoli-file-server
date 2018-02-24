package com.baoli.controller;

import com.baoli.exception.BLServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*******************************************
 * @Description: TODO
 * @Author: jerry.zheng
 * @Date Created in 14:43 2018\2\24 0024        
 *******************************************/

@Controller
public class TestExceptionController extends BaseController {

    @RequestMapping(value = "/testException", method = RequestMethod.GET)
    public void test() throws BLServiceException {
        throw new BLServiceException("testException", "error test");
    }

}
