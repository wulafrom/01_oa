package com.mashibing.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mashibing.entity.Account;
import com.mashibing.entity.Permission;
import com.mashibing.entity.PermissionExample;
import com.mashibing.mapper.AccountMapper;
import com.mashibing.mapper.PermissionMapper;
import com.mashibing.service.PermissionService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: h'mm
 * @date: 2021/3/10 19:37:06
 */
@Repository
public class PermissionServiceImpl implements PermissionService {

    PermissionMapper permissionMapper;
    AccountMapper accountMapper;

    public PermissionServiceImpl(PermissionMapper permissionMapper, AccountMapper accountMapper) {
        this.permissionMapper = permissionMapper;
        this.accountMapper = accountMapper;
    }

    @Override
    public PageInfo<Permission> selectByPermission(Integer pageNum, Integer pageSize) {
        List<Account> accounts = accountMapper.selectByPermission();

        return new PageInfo<>(accounts.get(0).getPermissionList());
    }

    @Override
    public Permission selectByPrimaryKey(Integer id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateByPrimaryKeySelective(Permission permission) {
        permissionMapper.updateByPrimaryKeySelective(permission);
    }

    @Override
    public void insertSelective(Permission permission) {
        permissionMapper.insertSelective(permission);
    }

    @Override
    public PageInfo<Permission> findByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PermissionExample example = new PermissionExample();
        return new PageInfo<>(permissionMapper.selectByExample(example));
    }


}
