package com.mashibing.service;

import com.github.pagehelper.PageInfo;
import com.mashibing.entity.Account;
import com.mashibing.entity.Role;
import org.springframework.stereotype.Service;

/**
 * @author: h'mm
 * @date: 2021/3/10 18:43:43
 */
@Service
public interface RoleService {
    /**
     * 分页查询 角色
     * @param pageNum 当前叶
     * @param pageSize 每页数量
     * @return 角色列表
     */
    PageInfo<Role> findByPage(Integer pageNum, Integer pageSize);

    /**
     * 根据id查询 角色
     * @param id 角色id
     * @return 角色对象
     */
    Role selectByPrimaryKey(Integer id);

    /**
     * 给角色重新分配权限
     * @param roleId 角色id
     * @param permissionId 权限id
     */
    void addRolePermission(Integer roleId, Integer permissionId);

    /**
     * 根据角色id 删除角色权限
     * @param roleId 角色权限
     */
    void deleteRolePermissionById(Integer roleId);

    /**
     * 根据角色id重新分配权限
     * @param roleId 角色id
     * @param permissions 权限id数组
     */
    void addRolePermissions(Integer roleId, Integer[] permissions);

    /**
     * 根据角色id查询角色及其权限信息
     * @param roleId 角色id
     * @return 角色和角色的权限
     */
    Role selectRoleAndPermissionByRoleId(Integer roleId);
}
