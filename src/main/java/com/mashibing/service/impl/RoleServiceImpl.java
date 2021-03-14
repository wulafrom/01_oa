package com.mashibing.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mashibing.entity.Account;
import com.mashibing.entity.Role;
import com.mashibing.entity.RoleExample;
import com.mashibing.mapper.RoleMapper;
import com.mashibing.service.RoleService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * @author: h'mm
 * @date: 2021/3/10 19:38:33
 */
@Repository
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public PageInfo<Role> findByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        RoleExample roleExample = new RoleExample();
        return new PageInfo<>(roleMapper.selectByExample(roleExample));
    }

    @Override
    public Role selectByPrimaryKey(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public void addRolePermission(Integer roleId, Integer permissionId) {
        roleMapper.addRolePermission(roleId,permissionId);
    }

    @Override
    public void deleteRolePermissionById(Integer roleId) {
        roleMapper.deleteRolePermissionById(roleId);
    }

    @Override
    public void addRolePermissions(Integer roleId, Integer[] permissions) {
        roleMapper.deleteRolePermissionById(roleId);
        roleMapper.addRolePermissions(roleId, permissions);
    }

    @Override
    public Role selectRoleAndPermissionByRoleId(Integer roleId) {
        return roleMapper.selectRoleAndPermissionByRoleId(roleId);
    }
}
