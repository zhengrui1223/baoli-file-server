package com.baoli.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/************************************************************
 * @author jerry.zheng
 * @Description
 * @date 2017-09-25 10:09
 ************************************************************/

@Controller
public class ErrorController {

    @RequestMapping("/e/404")
    public String get404Error(ModelMap modelMap) {
        modelMap.addAttribute("contentPath", "error/404");
        return "/layout";
    }

    @RequestMapping("/e/500")
    public String get500Error(ModelMap modelMap) {
        modelMap.addAttribute("contentPath", "error/500");
        return "/layout";
    }

    @RequestMapping("/e/400")
    public String get400Error(ModelMap modelMap) {
        modelMap.addAttribute("contentPath", "error/400");
        return "/layout";
    }
}
