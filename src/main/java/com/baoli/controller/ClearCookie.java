package com.baoli.controller;

import com.baoli.util.Context;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/************************************************************
 * @author jerry.zheng
 * @Description
 * @date 2017-09-30 9:29
 ************************************************************/

@Controller
public class ClearCookie {

    @RequestMapping("/clear")
    public String clearCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(Context.USER_INFO, "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/home";
    }

}
