package com.mashibing.controller;

import com.github.pagehelper.PageInfo;
import com.mashibing.entity.Account;
import com.mashibing.entity.Permission;
import com.mashibing.entity.Role;
import com.mashibing.service.AccountService;
import com.mashibing.service.PermissionService;
import com.mashibing.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 用户列表
     *
     * @param pageNum  当前叶
     * @param pageSize 每页数量
     * @param model    内置对象
     * @return 用户列表
     */
    @RequestMapping("/accountList")
    public String accountList(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "5") Integer pageSize, Model model) {
        PageInfo<Account> page = accountService.findByPage(pageNum, pageSize);
        model.addAttribute("page", page);
        return "manager/accountList";
    }

    /**
     * 角色列表
     *
     * @param pageNum  当前叶
     * @param pageSize 每页数量
     * @param model    内置对象
     * @return 角色列表
     */
    @RequestMapping("/roleList")
    public String roleList(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "5") Integer pageSize, Model model) {
        PageInfo<Role> page = roleService.findByPage(pageNum, pageSize);
        model.addAttribute("page", page);
        return "manager/roleList";
    }

    /**
     * 权限列表
     *
     * @param pageNum  当前叶
     * @param pageSize 每页数量
     * @param model    内置对象
     * @return 权限列表
     */
    @RequestMapping("/permissionList")
    public String permissionList(@RequestParam(defaultValue = "1") Integer pageNum,
                                 @RequestParam(defaultValue = "5") Integer pageSize, Model model) {
        PageInfo<Permission> page = permissionService.findByPage(pageNum, pageSize);
        model.addAttribute("page", page);
        return "manager/permissionList";
    }

    /**
     * 前往权限修改页面
     *
     * @param id    当前权限id
     * @param model 内置对象
     * @return 权限修改页面
     */
    @RequestMapping("/toPermissionModify")
    public String toPermissionModify(@RequestParam(name = "id") Integer id, Model model) {
        Permission permission = permissionService.selectByPrimaryKey(id);
        model.addAttribute("permission", permission);
        return "manager/permissionModify";
    }

    /**
     * 前往权限添加页面
     *
     * @return 权限新增页面
     */
    @RequestMapping("/toPermissionAdd")
    public String toPermissionAdd() {
        return "manager/permissionAdd";
    }


    /**
     * 前往权限分配页面
     *
     * @return 权限分配页面
     */
    @RequestMapping("/toRolePermission")
    public String toRolePermission(@RequestParam(name = "id") Integer roleId,
                                   @RequestParam(defaultValue = "1") Integer pageNum,
                                   @RequestParam(defaultValue = "5") Integer pageSize, Model model) {
        Role role = roleService.selectRoleAndPermissionByRoleId(roleId);

        //保存当前角色所拥有的权限id
        ArrayList<Integer> pLists = new ArrayList<>();
        List<Permission> permissions = role.getPermissionList();
        for (Permission permission : permissions) {
            pLists.add(permission.getId());
        }

        //当前系统所有的权限
        PageInfo<Permission> page = permissionService.findByPage(pageNum, pageSize);

        model.addAttribute("pLists", pLists);
        model.addAttribute("role", role);
        model.addAttribute("page", page);

        return "manager/rolePermission";
    }


}
