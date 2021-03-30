package com.mashibing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 入口
 * @author: h'mm
 * @date: 2021/3/6 0:14:01
 */

@Controller
public class MainController {

    /**
     * 首页跳转
     * @return 首页
     */
    @RequestMapping(value = "/")
    public String noPath(){
        return "index";
    }

    /**
     * 首页跳转
     * @return 首页
     */
    @RequestMapping(value = "/index")
    public String indexPath(){
        return "index";
    }

    /**
     * 错误页面跳转
     * @return 错误页面
     */
    @RequestMapping(value = "/errorPage")
    public String errorPage(){
        return "errorPage";
    }
}
