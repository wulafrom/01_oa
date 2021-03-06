package com.mashibing.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 登录拦截
 * @author: h'mm
 * @date: 2021/3/6 22:24:52
 */
@WebFilter
@Component
public class AccountFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(AccountFilter.class);



    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {

        HttpServletRequest servletRequest = (HttpServletRequest) request;
        String uri = servletRequest.getRequestURI();
        logger.info("---filter--- {}",uri);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
