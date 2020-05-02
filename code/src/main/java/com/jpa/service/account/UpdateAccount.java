package com.jpa.service.account;

import com.jpa.dao.AccountDao;
import com.jpa.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateAccount {
    @Autowired
    AccountDao accountDao;

    /*跟新数据*/
    public void Update(Account account) {
        accountDao.save(account);
    }

    /*采用Jpql 语句跟新数据*/
    /*采用jpql 语句跟新时间用 事务支持*/
    @Transactional
    public void UpdateByJpql(Float integer, String id) {
        accountDao.updateByJpql(integer, id);
    }
}
