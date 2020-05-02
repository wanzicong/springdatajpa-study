package com.jpa.service.account;

import com.jpa.dao.AccountDao;
import com.jpa.model.Account;
import net.bytebuddy.description.type.TypeDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class FindAccount {
    @Autowired
    AccountDao accountDao;

    /*1 查询所有*/
    public List<Account> findAll() {
        return accountDao.findAll();
    }

    /*2 根据ID查询*/
    public Account findOneById(String id) {
        /*Account one = accountDao.getOne(id);  //采用延迟加载*/
        return accountDao.findById(id).get();
    }

    /*3 根据名字查询*/
    public Account findOneByName(String name) {
        return accountDao.findByUsername(name);

    }

    /*4 查询记录的总条数*/
    public Long count() {
        return accountDao.count();
    }

    /*5 判断记录是否存在根据id */
    public Boolean exist(String id) {
        return accountDao.existsById(id);
    }

    /* 6 使用 原生自定义sql语句查询表*/
    public Account findByNameByJpql(String name) {
        return accountDao.findBySql(name);
    }

    /* 7 使用 jpql进行查询*/
    public Account findfindByJpql(String name) {
        return accountDao.findByJpql(name);
    }

    /*8 使用jpql 进行模糊查询*/
    public Account findfindByJpqlAndLike(String name) {
        return accountDao.findByJpqlAndLike(name);
    }

    /*9 使用命名规则进行模糊查询*/
    /*错误: 需要手动添加%% 在调用的时间*/
    public Account findByUsernameLike(String name) {
        return accountDao.findByUsernameLike("%" + name + "%");
    }

    /*10 命名规则进行多条件查询*/
    public Account findByUsernameAndMoney(String name, Float money) {
        return accountDao.findByUsernameAndMoney(name, money);
    }

    /*11 使用命名规则进行范围查询*/
    public List<Account> findByMoreThan(Float money) {
        return accountDao.findByMoneyIsGreaterThan(money);
    }

    /*-----------------------------------------------------------------------------以下是动态查询----------------------------------------------------------------------*/
    /*
     * 查询包含简单查询和复杂查询
     * 复查查询用到的特别的类的名字已经作用:
     * 名字:                  类 Specification (接口)
     *                                          参数:
     *                                                root  : 代表我们查询的根对象(查询的任何属性都可以从根对象中获取)
     *                                                 CridicateQuery   顶层查询对象(自定义查询方式)了解即可
     *                                                CridicateBuilder  查询的构造器 (分装了许多的查询条件) (比如迷糊匹配  等于 不等于...... )
     * 作用及使用:
     */

    /* 12 使用动态查询 查询一个*/
    public Account findOneBySpecification() {
        /*匿名内部类*/
        /*自定义查询条件*/
        /*多个条件查询*/
        Specification<Account> spe = new Specification<Account>() {
            /* root  对象   cd 对象*/
            @Override
            public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder cd) {
                Path<Object> username = root.get("username");
                Path<Object> money = root.get("money");
                /*第一个查询条件*/
                Predicate e1 = cd.equal(username, "万子聪");
                /*第二个查询条件*/
                Predicate e2 = cd.equal(money, 952f);
                /*将查询条件联合起来 分为两种情况*/
                Predicate predicate = cd.and(e1, e2);
                /*同时满足 和 只是满足一个条件*/
                /*cd.or();*/
                return predicate;
            }
        };
        Account account = accountDao.findOne(spe).get();
        return account;
    }

    /* 13 使用动态查询 查询多个*/
    public List<Account> findListBySpecification() {
        /*1  构造查询条件的匿名内部类*/
        Specification<Account> spe = new Specification<Account>() {
            @Override
            public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<Object> username = root.get("username");
                Predicate like = cb.like(username.as(String.class), "%乐%");
                return like;
            }
        };
        /*2  使用构造的查询条件*/

        /*排序的话,  创建排序的对象*/
        /*传递的参数:  1 倒叙还是正序  2 排序的属性名字 */
        Sort id = Sort.by(Sort.Direction.DESC, "id");
        return accountDao.findAll(spe, id);
    }

    /* 14 使用动态查询进行分页*/
    public List<Account> findListBySpecificationPage() {
        Specification spe = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };
        Pageable pageable = PageRequest.of(1, 5);
        Sort sort = Sort.by(Sort.Direction.DESC, "money");
        Page pages = accountDao.findAll(spe, pageable);
        return pages.getContent();
    }

    /*15 使用一下动态查询的条件构造动态查询*/
    public List<Account> findBySpes() {
        Account account = new Account();
        account.setId("7");
//        account.setUsername("万");
//        account.setMoney(9f);
        Specification spe = createSpe(account);
        return accountDao.findAll(spe);
    }

    /*-----------------------------------------------------------------------------动态条件的构建----------------------------------------------------------------------*/
    private Specification createSpe(Account map) {
        /*返回一个构造好的spe --动态查询条件*/
        List<Predicate> list = new ArrayList<>();
        return new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cd) {



                /*获取出入的查询条件*/
                if (map.getId() != null && !"".equals(map.getId())) {
                    /*得到查询条件的名字*/
                    Path id = root.get("id");
                    /*构造模糊查询的条件*/
                    Predicate like1 = cd.like(id.as(String.class), "%" + (String) map.getId() + "%");
                    /*将这个条件保存到一个列表中最后返回*/
                    list.add(like1);
                }


                /*获取出入的查询条件*/
                if (map.getUsername() != null && !"".equals(map.getUsername())) {
                    /*重复上述步骤*/
                    Path username = root.get("username");
                    Predicate like2 = cd.like(username.as(String.class), "%" + (String) map.getUsername() + "%");
                    list.add(like2);
                }


                /*获取出入的查询条件*/
                if (map.getMoney() != null) {
                    Path money = root.get("money");
                    Predicate like3 = cd.equal(money, (Float) map.getMoney());
                    list.add(like3);
                }

                /*将列表中的所有条件用and 连接起来返回 一个大的predicate*/
                Predicate all = cd.and(list.toArray(new Predicate[list.size()]));
                return all;
            }
        };
    }
}
