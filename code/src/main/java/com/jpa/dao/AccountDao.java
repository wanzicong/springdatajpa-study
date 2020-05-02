package com.jpa.dao;

import com.jpa.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Column;
import java.util.List;


/*Account 实体类  String 主键类型
 * JpaRepository<Account,String>,JpaSpecificationExecutor<Account>
 */
public interface AccountDao extends JpaRepository<Account, String>, JpaSpecificationExecutor<Account> {

    /*命名规则自定义查询*/
    /*总结:  findBy++++ 后面跟的是java代码里面的字段而不是数据库里面的字段
     * java 代码可以通过@Colum(name = "") 注解来自定义属性的字段。
     */
    Account findAccountByUsername(String name);

    /*命名规则自定义查询*/
    /*总结 :  findBy 后面可以不用加类名*/
    Account findByUsername(String name);

    /*命名规则自定义查询*/
    Account findAccountsByUsernameStartsWith(String name);

    /*命名规则自定义查询*/
    /* 进行模糊查询*/
    /*错误: 需要手动添加%% 在调用的时间*/
    Account findByUsernameLike(String name);

    /*使用原生sql查询 使用@Query注解 */
    @Query(value = "select  * from account  a where  a.name =?1", nativeQuery = true)
    Account findBySql(String username);

    /*使用jpql来查询*/
    @Query(value = "from Account  where username=?1")
    Account findByJpql(String username);

    /*根据Jpql更新操作*/
    /*调用时间需要用事务*/
    @Query(value = "update  Account  set money = money+?1 where id = ?2")
    @Modifying
    void updateByJpql(Float integer, String id);

    /*使用jpql进行模糊查询*/
    /*总结： 和写sql 语句都差不多 只是使用的属性是 java代码里面自定义的属性*/
    /*错误提示: 使用关键字 下面参数要添加  @Param("username") 注解*/
    @Query(value = "from Account where username like  %:username%")
    Account findByJpqlAndLike(@Param("username") String username);

    /*多条件的查询*/
    Account findByUsernameAndMoney(String name, Float money);

    /*命名规则*/
    /*大于  money的条件*/
    List<Account> findByMoneyIsGreaterThan(Float money);

    /*-------------------------------------------------------------以下是动态查询------------------------------------------------------*/

}
