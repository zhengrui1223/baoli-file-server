package com.baoli.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/************************************************************
 * @author jerry.zheng
 * @Description
 * @date 2017-09-25 10:09
 ************************************************************/

@Controller
public class ErrorController {

    @RequestMapping("/e/404")
    public String get404Error() {
        return "/error/404";
    }

    @RequestMapping("/e/500")
    public String get500Error() {
        return "/error/500";
    }

    @RequestMapping("/e/400")
    public String get400Error() {
        return "/error/400";
    }
}
