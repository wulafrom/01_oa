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
import java.util.Objects;

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
    @RequestMapping(value = "/logOut")
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
        return "/account/list";
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
     * 上传用户头像到项目下
     * @param filename 文件名称
     * @param password 用户密码
     * @return 用户信息页面
     */
    @RequestMapping("/fileUploadController/v1")
    public String fileUploadV1 (MultipartFile filename, String password,HttpServletRequest request) {
        logger.info("password: {}" , password);
        logger.info("file: {}" , filename.getOriginalFilename());
        Account account = (Account)request.getSession().getAttribute("account");
        try {

            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            File upload = new File(path.getAbsolutePath(), "static/upload/");

            if (!upload.exists()) {
                upload.mkdirs();
            }

            logger.info("upload: {}" , upload);

            filename.transferTo(new File(upload , Objects.requireNonNull(filename.getOriginalFilename())));

            account.setLocation(filename.getOriginalFilename());
            account.setPassword(password);
            accountService.updateByPrimaryKeySelective(account);

        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        return "/account/profile";
    }

    /**
     * 上传用户头像到本地硬盘
     * @param filename 文件名称
     * @param password 用户密码
     * @return 用户信息页面
     */
    @RequestMapping("/fileUploadController/v2")
    public String fileUploadV2 (MultipartFile filename, String password,HttpServletRequest request) {
        Account account = (Account)request.getSession().getAttribute("account");
        try {

            filename.transferTo(new File("D:\\upload" , Objects.requireNonNull(filename.getOriginalFilename())));

            account.setLocation(filename.getOriginalFilename());
            account.setPassword(password);
            accountService.updateByPrimaryKeySelective(account);

        } catch (IllegalStateException | IOException e) {
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
            logger.info("upload: {}",upload.getAbsolutePath());
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
        return "/account/profile";
    }


}
