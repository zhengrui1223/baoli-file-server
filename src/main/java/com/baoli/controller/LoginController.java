package com.baoli.controller;

import com.baoli.util.Context;
import com.baoli.util.CookieUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/************************************************************
 * @author jerry.zheng
 * @Description
 * @date 2017-09-25 8:46
 ************************************************************/

@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String toLoginPage(HttpServletResponse response) {
        CookieUtil.setCookie(response, Context.USER_INFO, null, 0);
        return "login/sign-in";
    }

    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public String doLogin(@RequestParam String userName, @RequestParam String passWord,
                          @RequestParam(required = false) boolean remember, HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.setCookie(response, Context.USER_INFO, "BAO.LI.USER.INFO", 0);
        request.getSession().setAttribute("userName", userName);
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index(ModelMap modelMap) {
        modelMap.addAttribute("contentPath", "home");
        return "/layout";
    }

}
