package com.mashibing.controller;

import com.github.pagehelper.PageInfo;
import com.mashibing.entity.Account;
import com.mashibing.entity.Permission;
import com.mashibing.entity.SystemConfig;
import com.mashibing.service.AccountService;
import com.mashibing.service.PermissionService;
import com.mashibing.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * RBAC权限管理
 *
 * @author: h'mm
 * @date: 2021/3/10 18:12:18
 */
@Controller
@RequestMapping("/manager")
public class ManagerController {
    private final Logger logger = LoggerFactory.getLogger(ManagerController.class);

    private final AccountService accountService;
    private final RoleService roleService;
    private final PermissionService permissionService;

    public ManagerController(AccountService accountService, RoleService roleService,
                             PermissionService permissionService) {
        this.accountService = accountService;
        this.roleService = roleService;
        this.permissionService = permissionService;
    }

    @RequestMapping("/accountList")
    public String accountList(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "5") Integer pageSize, Model model) {
        PageInfo<Account> page = accountService.findByPage(pageNum, pageSize);
        model.addAttribute("page", page);
        return "/manager/accountList";
    }

    @RequestMapping("/roleList")
    public String roleList(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "5") Integer pageSize, Model model) {
        PageInfo<Account> page = accountService.findByPage(pageNum, pageSize);
        model.addAttribute("page", page);
        return "/manager/roleList";
    }

    @RequestMapping("/permissionList")
    public String permissionList(@RequestParam(defaultValue = "1") Integer pageNum,
                                 @RequestParam(defaultValue = "5") Integer pageSize, Model model) {
        PageInfo<Permission> page = permissionService.findByPage(pageNum, pageSize);
        model.addAttribute("page", page);
        return "/manager/permissionList";
    }

    @RequestMapping("/toPermissionModify")
    public String toPermissionModify(@RequestParam(name = "id") Integer id, Model model) {
        Permission permission = permissionService.selectByPrimaryKey(id);
        model.addAttribute("permission", permission);
        return "/manager/permissionModify";
    }

    @RequestMapping("/toPermissionAdd")
    public String toPermissionAdd() {
        return "/manager/permissionAdd";
    }
}
