package com.mashibing.service;

import com.github.pagehelper.PageInfo;
import com.mashibing.RespStat;
import com.mashibing.entity.Account;
import org.springframework.stereotype.Service;

/**
 * @author: h'mm
 * @date: 2021/3/6 0:24:12
 */
@Service
public interface AccountService {

    /**
     *  根据名称和密码查询用户
     * @param loginName 登录名
     * @param password 密码
     * @return 用户信息
     */
     Account findByLoginNameAndPassword(String loginName, String password);

    /**
     *  分页查询用户
     * @param pageNum 当前页码
     * @param pageSize 每页个数
     * @return 多个用户信息
     */
     PageInfo<Account> findByPage(int pageNum, int pageSize);

    /**
     * 根据id删除用户
     * @param id id
     * @return 删除结果
     */
    RespStat deleteById(Integer id);

    /**
     * 根据id修改用户头像和密码
     * @param account 从session中获取的用户
     */
    void updateByPrimaryKeySelective(Account account);
}
