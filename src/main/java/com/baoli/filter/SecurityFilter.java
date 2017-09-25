package com.baoli.filter;

import com.baoli.util.Config;
import com.baoli.util.Context;
import com.baoli.util.CookieUtil;
import com.baoli.util.StringPool;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/************************************************************
 * @author jerry.zheng
 * @Description
 * @date 2017-09-25 8:41
 ************************************************************/

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private Config config;

    private static String[] notFilter = null;

    @PostConstruct
    public void init() {
        loadConfig();
    }

    protected void loadConfig() {
        //权限过滤URI
        if (StringUtils.isNotEmpty(config.getIgnoreUrl())) {
            String str = config.getIgnoreUrl().replaceAll(StringPool.SPACE, StringPool.BLANK);
            notFilter = StringUtils.split(str, StringPool.COMMA);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie cookie = CookieUtil.getCookieByName(request, Context.USER_INFO);
        boolean needFilter = needFilter(request.getRequestURI());
        if (needFilter) {
            if (cookie == null || cookie.getValue() == null) {
                response.sendRedirect(request.getContextPath() + "/login");
            }else {
                filterChain.doFilter(request, response);
            }
        }else {
            filterChain.doFilter(request, response);
        }

    }

    protected boolean needFilter(String uri) {
        boolean doFilter = true;
        if (null == notFilter) {
            return true;
        }

        for (String s : notFilter) {
            if (uri.contains(s)) {
                doFilter = false;/**  如果uri中包含不过滤的uri，则不进行过滤 */
                break;
            }
        }
        return doFilter;
    }

}
