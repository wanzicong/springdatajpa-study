package com.jpa.service.account;
import com.jpa.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class DeleteAccount {
    @Autowired
    AccountDao accountDao;
    /*根据id删除数据*/
    public void delete(String id) {
        accountDao.deleteById(id);
    }
}
