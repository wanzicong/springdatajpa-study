package com.jpa.service.account;

import com.jpa.dao.AccountDao;
import com.jpa.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InsertAccount {
    @Autowired
    AccountDao accountDao;

    /*使用的是添加操作*/
    public void insert(Account account) {
        accountDao.save(account);
    }
}
