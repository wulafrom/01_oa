package com.mashibing.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录拦截
 *
 * @author: h'mm
 * @date: 2021/3/6 22:24:52
 */
@WebFilter
@Component
public class AccountFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(AccountFilter.class);

    private final String[] ignorePath = {"/js","/account/login","/account/validateAccount","/images","/css","/index","/favicon"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {

        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        String uri = servletRequest.getRequestURI();
        logger.info("---filter--- {}", uri);

        //当前访问路径
        boolean pass = canPassIgnore(uri);
        if (pass) {
            chain.doFilter(request, response);
            return;
        }

        //从session中获取用户
        Object account = servletRequest.getSession().getAttribute("account");
        if (account != null) {
            chain.doFilter(request, response);
            return;
        }

        //重定向到登录页面
        servletResponse.sendRedirect("/account/login");
    }

    private boolean canPassIgnore(String uri) {

        for (String path : ignorePath) {
            if (uri.startsWith(path)) {
                return true;
            }
        }
        return false;
    }
}
