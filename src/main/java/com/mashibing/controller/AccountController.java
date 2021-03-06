package com.mashibing.controller;

import com.mashibing.entity.Account;
import com.mashibing.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户接口
 *
 * @author: h'mm
 * @date: 2021/3/6 0:22:14
 */
@Controller
@RequestMapping(value = "/account")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * 登录页面跳转
     *
     * @return 页面路径
     */
    @RequestMapping("/login")
    public String login() {

        return "account/login";
    }


    /**
     * 登录校验
     *
     * @param loginName 用户名
     * @param password  密码
     * @param request
     * @return
     */
    @RequestMapping(value = "/validateAccount")
    @ResponseBody
    public String validateAccount(String loginName, String password, HttpServletRequest request) {

        Account account = accountService.findByLoginNameAndPassword(loginName, password);
        if (account == null) {
            return "fail";
        }
        request.getSession().setAttribute("account", account);
        return "success";
    }
}
