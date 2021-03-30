package com.mashibing.filter;

import com.mashibing.entity.Account;
import com.mashibing.entity.Permission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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

    private final String[] ignorePath = {"/js", "/account/login", "/account/validateAccount", "/images", "/css",
            "/favicon" + "/errorPage"};

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
        Account account = (Account) servletRequest.getSession().getAttribute("account");
        if (account != null) {

            //判断用户是否有当前接口的权限
            List<Permission> permissionList = account.getPermissionList();
            if (noAccess(permissionList, uri)) {
                request.setAttribute("msg", "您没有访问权限，请联系管理员添加权限: " + uri);
                request.getRequestDispatcher("/errorPage").forward(servletRequest, servletResponse);
                return;
            }

            chain.doFilter(request, response);
            return;
        }

        //重定向到登录页面
        servletResponse.sendRedirect("/account/login");
    }

    /**
     * 判断当前uri不在用户的访问权限里
     *
     * @param permissionList 当前用户所有的访问权限
     * @param uri            当前访问路径
     * @return 当前用户没有访问权限
     */
    private boolean noAccess(List<Permission> permissionList, String uri) {
        if (null != permissionList) {
            for (Permission permission : permissionList) {
                if (uri.startsWith(permission.getUri())) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean canPassIgnore(String uri) {

        for (String path : ignorePath) {
            if (!"/".equals(uri) && uri.startsWith(path)) {
                return true;
            }
        }
        return false;
    }
}
