package com.jpa.account;

import com.jpa.model.Account;
import com.jpa.model.Roles;
import com.jpa.service.account.FindAccount;
import com.jpa.service.account.UpdateAccount;
import com.jpa.service.roles.FindRoles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AccountTest {
    @Autowired
    FindAccount findAccount;
    @Autowired
    UpdateAccount updateAccount;
    @Autowired
    FindRoles findRoles;

    @Test
    void find() {
        List<Account> bySpes = findAccount.findBySpes();
        for (Account bySpe : bySpes) {
            System.out.println("-------------------");
            System.out.println(bySpe);
        }
    }
}
