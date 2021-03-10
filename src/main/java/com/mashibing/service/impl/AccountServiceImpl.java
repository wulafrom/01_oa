package com.mashibing.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mashibing.RespStat;
import com.mashibing.entity.Account;
import com.mashibing.entity.AccountExample;
import com.mashibing.mapper.AccountMapper;
import com.mashibing.service.AccountService;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * AccountService 方法实现
 *
 * @author: h'mm
 * @date: 2021/3/6 0:24:45
 */
@Repository
public class AccountServiceImpl implements AccountService {

    private final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
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

        List<Account> aList = accountMapper.selectByPermission();
        String aString = aList.get(0).toString();
        logger.info("用户角色: {}", aString);

        PageHelper.startPage(pageNum, pageSize);
        List<Account> accounts = accountMapper.selectByExample(new AccountExample());


        return new PageInfo<>(accounts, 5);
    }

    @Override
    public RespStat deleteById(Integer id) {

        int status = accountMapper.deleteByPrimaryKey(id);

        if (status == 1) {
            return RespStat.build(200);
        }

        return RespStat.build(500, "删除错误");
    }
}
