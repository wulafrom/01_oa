package com.mashibing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: h'mm
 * @date: 2021/3/6 0:14:01
 */

@Controller
public class MainController {

    @RequestMapping(value = "/")
    public String noPath(){
        return "index";
    }

    @RequestMapping(value = "/index")
    public String indexPath(){
        return "index";
    }
}
