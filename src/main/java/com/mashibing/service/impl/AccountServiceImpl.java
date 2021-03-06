package com.mashibing.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mashibing.entity.Account;
import com.mashibing.entity.AccountExample;
import com.mashibing.mapper.AccountMapper;
import com.mashibing.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * AccountService 方法实现
 * @author: h'mm
 * @date: 2021/3/6 0:24:45
 */

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountMapper accountMapper;

    @Autowired
    public AccountServiceImpl(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    @Override
    public Account findByLoginNameAndPassword(String loginName, String password) {
        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria()
                .andLoginNameEqualTo(loginName)
                .andPasswordEqualTo(password);
        List<Account> accounts = accountMapper.selectByExample(accountExample);
        return accounts.isEmpty() ? null : accounts.get(0);
    }

    @Override
    public PageInfo<Account> findByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Account> accounts = accountMapper.selectByExample(new AccountExample());
        return new PageInfo<>(accounts);
    }
}
