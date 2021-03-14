package com.mashibing.controller.rest;

import com.mashibing.RespStat;
import com.mashibing.entity.Permission;
import com.mashibing.service.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * restful风格的controller
 *
 * @author: h'mm
 * @date: 2021/3/11 0:50:50
 */
@RestController
@RequestMapping("/api/v1/manager/permission")
public class ManagerRestController {

    private final Logger logger = LoggerFactory.getLogger(ManagerRestController.class);
    PermissionService permissionService;

    public ManagerRestController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    /**
     * 修改权限信息
     * @param permission 权限信息详情
     * @return 修改状态
     */
    @RequestMapping(value = "/update")
    public RespStat update(@RequestBody Permission permission) {

        logger.info("permission: {}", permission);
        permissionService.updateByPrimaryKeySelective(permission);
        return RespStat.build(200);
    }

    /**
     * 添加权限
     * @param permission 要添加权限的信息
     * @return 添加权限的状态
     */
    @RequestMapping(value = "/add")
    public RespStat add(@RequestBody Permission permission) {

        logger.info("permission: {}", permission);
        permissionService.insertSelective(permission);
        return RespStat.build(200);
    }
}
