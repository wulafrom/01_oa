package com.mashibing.controller.rest;

import com.mashibing.RespStat;
import com.mashibing.mapper.RoleMapper;
import com.mashibing.service.PermissionService;
import com.mashibing.service.RoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * rest 风格的接口
 *
 * @author: h'mm
 * @date: 2021/3/12 23:52:50
 */
@RestController
@RequestMapping(value = "/api/manager/role")
public class RoleRestController {

    RoleService roleService;
    PermissionService permissionService;

    public RoleRestController(RoleService roleService, PermissionService permissionService) {
        this.roleService = roleService;
        this.permissionService = permissionService;
    }

    @RequestMapping(value = "/permissionAdd/v1")
    public RespStat permissionAddV1(@RequestParam(value = "id") Integer roleId,
                                @RequestParam(value = "permissions") Integer[] permissions) {

        roleService.deleteRolePermissionById(roleId);

        for (Integer permissionId : permissions) {
            roleService.addRolePermission(roleId, permissionId);
        }
        return RespStat.build(200);
    }

    @RequestMapping(value = "/permissionAdd/v2")
    public RespStat permissionAddV2(@RequestParam(value = "id") Integer roleId,
                                @RequestParam(value = "permissions") Integer[] permissions) {

        roleService.addRolePermissions(roleId,permissions);
        return RespStat.build(200);
    }

}
