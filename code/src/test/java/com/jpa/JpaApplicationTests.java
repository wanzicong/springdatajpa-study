package com.jpa;

import com.jpa.model.Account;
import com.jpa.service.account.FindAccount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class JpaApplicationTests {

    @Autowired
    FindAccount accountDao;

    @Test
    void contextLoads() {
        List<Account> all = accountDao.findAll();
        System.out.println(all);
    }
}
