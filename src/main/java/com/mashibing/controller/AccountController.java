package com.mashibing.controller;

import com.github.pagehelper.PageInfo;
import com.mashibing.RespStat;
import com.mashibing.entity.Account;
import com.mashibing.entity.SystemConfig;
import com.mashibing.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;

    private final SystemConfig systemConfig;

    public AccountController(AccountService accountService, SystemConfig systemConfig) {
        this.accountService = accountService;
        this.systemConfig = systemConfig;
    }

    /**
     * 登录页面跳转
     *
     * @return 页面路径
     */
    @RequestMapping("/login")
    public String login(Model model) {
        logger.info("config: {}", systemConfig.getSystemName());
        model.addAttribute("systemName",systemConfig.getSystemName());
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


    /**
     * 分页获取用户数据
     * @param pageNum 当前页
     * @param pageSize 每页数量
     * @param model 模板引擎内置对象
     * @return 数据列表页面
     */
    @RequestMapping("/list")
    public String getList(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "5")Integer pageSize, Model model) {

        PageInfo<Account> page = accountService.findByPage(pageNum, pageSize);
        model.addAttribute("page", page);
        return "/list";
    }

    /**
     * 根据id从逻辑删除用户
     * @param id 用户id
     * @return 删除状态情况
     */
    @RequestMapping(value = "/deleteById")
    @ResponseBody
    public RespStat deleteById(@RequestParam("id") Integer id){
        RespStat respStat=accountService.deleteById(id);
        respStat.setMsg("no data");
        return respStat;
    }


    /**
     * 上传用户文件
     * @param filename 文件名称
     * @param password 用户密码
     * @return 用户信息页面
     */
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

    /**
     * 路径跳转
     * @return 用户信息页面
     */
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
