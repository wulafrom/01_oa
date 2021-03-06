package com.mashibing.service;

import com.github.pagehelper.PageInfo;
import com.mashibing.entity.Account;

/**
 * @author: h'mm
 * @date: 2021/3/6 0:24:12
 */
public interface AccountService {


    /**
     *  根据名称和密码查询用户
     * @param loginName 登录名
     * @param password 密码
     * @return 用户信息
     */
    public Account findByLoginNameAndPassword(String loginName, String password);

    /**
     *  分页查询用户
     * @param pageNum 当前页码
     * @param pageSize 每页个数
     * @return 多个用户信息
     */
    public PageInfo<Account> findByPage(int pageNum, int pageSize);
}
