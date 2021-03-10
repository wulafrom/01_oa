package com.mashibing.service;

import com.github.pagehelper.PageInfo;
import com.mashibing.entity.Permission;
import org.springframework.stereotype.Service;

/**
 * @author: h'mm
 * @date: 2021/3/10 18:43:30
 */
@Service
public interface PermissionService {
    /**
     * 分页查询 权限信息
     * @param pageNum 当前叶
     * @param pageSize 每页数量
     * @return 当前叶的权限信息
     */
    PageInfo<Permission> findByPage(Integer pageNum, Integer pageSize);

    /**
     * 根据id查询 权限详细信息
     * @param id
     * @return
     */
    Permission selectByPrimaryKey(Integer id);

    /**
     * 根据id更新要修改的数据
     * @param permission 权限信息
     */
    void updateByPrimaryKeySelective(Permission permission);

    /**
     *  增加权限
     * @param permission 权限信息
     */
    void insertSelective(Permission permission);
}
