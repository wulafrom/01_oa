package com.mashibing.controller;

import com.github.pagehelper.PageInfo;
import com.mashibing.RespStat;
import com.mashibing.entity.Account;
import com.mashibing.service.AccountService;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 用户接口
 *
 * @author: h'mm
 * @date: 2021/3/6 0:22:14
 */
@Controller
@RequestMapping(value = "/account")
public class AccountController {

    private final AccountService accountService;

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
     * @param request   请求对象
     * @return 查询信息
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

    /**
     * 登出
     *
     * @param request 请求对象
     * @return 返回到index页面
     */
    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("account");
        return "index";
    }


    @RequestMapping("/list")
    public String getList(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "5")Integer pageSize, Model model) {

        PageInfo<Account> page = accountService.findByPage(pageNum, pageSize);
        model.addAttribute("page", page);
        return "/list";
    }

    @RequestMapping(value = "/deleteById")
    @ResponseBody
    public RespStat deleteById(@RequestParam("id") Integer id){
        RespStat respStat=accountService.deleteById(id);
        respStat.setMsg("no data");
        return respStat;
    }

    @RequestMapping("/fileUploadController")
    public String fileUpload (MultipartFile filename, String password) {
        System.out.println("password:" + password);
        System.out.println("file:" + filename.getOriginalFilename());
        try {

            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            File upload = new File(path.getAbsolutePath(), "static/upload/");
            ResourceProperties resourceProperties = new ResourceProperties();
            for (String staticLocation : resourceProperties.getStaticLocations()) {
                System.out.println(staticLocation);
            }
            String uploadPath = resourceProperties.getStaticLocations()[3];

            System.out.println("upload:" + uploadPath);

            filename.transferTo(new File("D:/upload/"+filename.getOriginalFilename()));


        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "/account/profile";
    }

    @RequestMapping("/profile")
    public String profile () {

        try {
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            File upload = new File(path.getAbsolutePath(), "static/upload/");
            System.out.println(upload.getAbsolutePath());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        };
        return "/account/profile";
    }


}
