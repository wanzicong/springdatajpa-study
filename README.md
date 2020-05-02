springData框架的学习

增 删 改 查的解读

		1.查询全部
		2.分页查询
		3.统计查询
		4.条件查询

```java
最容易的：

删除： 根据id 删除最长用  在springdata 框架中只用 接口中提供的方法 deleteById（）；

添加：在数据库中添加一个数据  在springdata中通常调用借口中的save () 方法

比较容易的：

更新：  1. 只跟新个别字段   2. 跟新所有的字段或者大多数字段

1.在springdata 中只跟新个别字段 通常只是采用 jpql 方法通过写类似于 sql 语句的形式进行跟新

只是在这些语句中采用的字段是java类中的属性而不是表中的字段  在调用时间需要在业务中添加事务

2.在springdata中通常采用的方式是调用接口中提供的sava() 方法 -->也可以用于保存操作

比较复杂的

查询： 

1.查询全部 --->在springdata中采用接口中的方法

2.根据id查询---> 在springdata中采用接口的方法

3.根据别的其他字段查询 ----> 可以使用jpql的方式进行查询 也可以使用命名规则进行查询

4.使用两个以上的一些字段查询 ---->   可以使用jpql的方式进行查询 也可以使用命名规则进行查询

5.查询全部并且分页  ----> 可以使用特殊类 pageable 类构造查询的参数

6.带一个或者多个的条件的并且进行分页  可以使用特殊类 pageable 类构造查询的参数

7.查询之后进行排序  ----> 使用 Sort 类进行构造查询排序条件

8.动态查询 并进行分页 -----> 动态查询使用specification 类进行构造 动态查询的条件

9.模糊查询  -----> 使用命名规则中特殊关键字进行查询 也可以根据jpql  语句进行查询  注意%%需要自己添加

10.类似大于小于 这样的特殊条件的查询  ---->大多数采用命名规则进行查询


```



springdata中具体的使用细节

```
复杂查询
  			    i.借助接口中的定义好的方法完成查询
                    findOne(id):根据id查询
                ii.jpql的查询方式
                    jpql ： jpa query language  （jpq查询语言）
                    特点：语法或关键字和sql语句类似
                        查询的是类和类中的属性

                    * 需要将JPQL语句配置到接口方法上
                        1.特有的查询：需要在dao接口上配置方法
                        2.在新添加的方法上，使用注解的形式配置jpql查询语句
                        3.注解 ： @Query

                iii.sql语句的查询
                        1.特有的查询：需要在dao接口上配置方法
                        2.在新添加的方法上，使用注解的形式配置sql查询语句
                        3.注解 ： @Query
                            value ：jpql语句 | sql语句
                            nativeQuery ：false（使用jpql查询） | true（使用本地查询：sql查询）
                                是否使用本地查询

                iiii.方法名称规则查询              
```

​		

根据命名规则查询的技巧

```java
命名规则查询总结: 

find+全局修饰+By+实体属性名称+限定词+连接词+（其他实体属性）+OrderBy+排序属性+排序方向

比如: 										findDistinctByFirstNameIgnoreCaseAndLastNameOrderByAgeDesc(String first,String last)

全局修饰符：distinct,top,first    
关键词（限定词+连接词）：IsNull,IsNotNull,Like,NotLike,Containing,In,NotIn,IgnoreCase,Between,Equals,LessThan,GreaterThan,After,Before
排序方向：Asc,Desc    
```



| `And`               | `findByLastnameAndFirstname`                                 | `… where x.lastname = ?1 and x.firstname = ?2`               |
| ------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| `Or`                | `findByLastnameOrFirstname`                                  | `… where x.lastname = ?1 or x.firstname = ?2`                |
| `Is,Equals`         | `findByFirstname`,`findByFirstnameIs`,`findByFirstnameEquals` | `… where x.firstname = ?1`                                   |
| `Between`           | `findByStartDateBetween`                                     | `… where x.startDate between ?1 and ?2`                      |
| `LessThan`          | `findByAgeLessThan`                                          | `… where x.age < ?1`                                         |
| `LessThanEqual`     | `findByAgeLessThanEqual`                                     | `… where x.age <= ?1`                                        |
| `GreaterThan`       | `findByAgeGreaterThan`                                       | `… where x.age > ?1`                                         |
| `GreaterThanEqual`  | `findByAgeGreaterThanEqual`                                  | `… where x.age >= ?1`                                        |
| `After`             | `findByStartDateAfter`                                       | `… where x.startDate > ?1`                                   |
| `Before`            | `findByStartDateBefore`                                      | `… where x.startDate < ?1`                                   |
| `IsNull`            | `findByAgeIsNull`                                            | `… where x.age is null`                                      |
| `IsNotNull,NotNull` | `findByAge(Is)NotNull`                                       | `… where x.age not null`                                     |
| `Like`              | `findByFirstnameLike`                                        | `… where x.firstname like ?1`                                |
| `NotLike`           | `findByFirstnameNotLike`                                     | `… where x.firstname not like ?1`                            |
| `StartingWith`      | `findByFirstnameStartingWith`                                | `… where x.firstname like ?1`(parameter bound with appended `%`) |
| `EndingWith`        | `findByFirstnameEndingWith`                                  | `… where x.firstname like ?1`(parameter bound with prepended `%`) |
| `Containing`        | `findByFirstnameContaining`                                  | `… where x.firstname like ?1`(parameter bound wrapped in `%`) |
| `OrderBy`           | `findByAgeOrderByLastnameDesc`                               | `… where x.age = ?1 order by x.lastname desc`                |
| `Not`               | `findByLastnameNot`                                          | `… where x.lastname <> ?1`                                   |
| `In`                | `findByAgeIn(Collection ages)`                               | `… where x.age in ?1`                                        |
| `NotIn`             | `findByAgeNotIn(Collection age)`                             | `… where x.age not in ?1`                                    |
| `True`              | `findByActiveTrue()`                                         | `… where x.active = true`                                    |
| `False`             | `findByActiveFalse()`                                        | `… where x.active = false`                                   |
| `IgnoreCase`        | `findByFirstnameIgnoreCase`                                  | `… where UPPER(x.firstame) = UPPER(?1)`                      |



| Keyword             | Sample                                                       | JPQL snippet                                                 |
| :------------------ | :----------------------------------------------------------- | :----------------------------------------------------------- |
| `And`               | `findByLastnameAndFirstname`                                 | `… where x.lastname = ?1 and x.firstname = ?2`               |
| `Or`                | `findByLastnameOrFirstname`                                  | `… where x.lastname = ?1 or x.firstname = ?2`                |
| `Is,Equals`         | `findByFirstname`,`findByFirstnameIs`,`findByFirstnameEquals` | `… where x.firstname = ?1`                                   |
| `Between`           | `findByStartDateBetween`                                     | `… where x.startDate between ?1 and ?2`                      |
| `LessThan`          | `findByAgeLessThan`                                          | `… where x.age < ?1`                                         |
| `LessThanEqual`     | `findByAgeLessThanEqual`                                     | `… where x.age <= ?1`                                        |
| `GreaterThan`       | `findByAgeGreaterThan`                                       | `… where x.age > ?1`                                         |
| `GreaterThanEqual`  | `findByAgeGreaterThanEqual`                                  | `… where x.age >= ?1`                                        |
| `After`             | `findByStartDateAfter`                                       | `… where x.startDate > ?1`                                   |
| `Before`            | `findByStartDateBefore`                                      | `… where x.startDate < ?1`                                   |
| `IsNull`            | `findByAgeIsNull`                                            | `… where x.age is null`                                      |
| `IsNotNull,NotNull` | `findByAge(Is)NotNull`                                       | `… where x.age not null`                                     |
| `Like`              | `findByFirstnameLike`                                        | `… where x.firstname like ?1`                                |
| `NotLike`           | `findByFirstnameNotLike`                                     | `… where x.firstname not like ?1`                            |
| `StartingWith`      | `findByFirstnameStartingWith`                                | `… where x.firstname like ?1`(parameter bound with appended `%`) |
| `EndingWith`        | `findByFirstnameEndingWith`                                  | `… where x.firstname like ?1`(parameter bound with prepended `%`) |
| `Containing`        | `findByFirstnameContaining`                                  | `… where x.firstname like ?1`(parameter bound wrapped in `%`) |
| `OrderBy`           | `findByAgeOrderByLastnameDesc`                               | `… where x.age = ?1 order by x.lastname desc`                |
| `Not`               | `findByLastnameNot`                                          | `… where x.lastname <> ?1`                                   |
| `In`                | `findByAgeIn(Collection ages)`                               | `… where x.age in ?1`                                        |
| `NotIn`             | `findByAgeNotIn(Collection age)`                             | `… where x.age not in ?1`                                    |
| `True`              | `findByActiveTrue()`                                         | `… where x.active = true`                                    |
| `False`             | `findByActiveFalse()`                                        | `… where x.active = false`                                   |
| `IgnoreCase`        | `findByFirstnameIgnoreCase`                                  | `… where UPPER(x.firstame) = UPPER(?1)`                      |



进行分页查询的类的细节:

```java
Pageable pageable = PageRequest.of(1, 5);
```

进行排序的类的细节:

```java
Sort id = Sort.by(Sort.Direction.DESC, "id");
```

调用这两个参数: 

```java
Page pages = accountDao.findAll(spe, pageable);
```



定义：java类的模板

```java
package com.jpa.model;
import lombok.Data;
import javax.persistence.*;

/*这是一个完整的配置注解*/
/*这是一个lomk插件 自动生成getter 和 setter*/
@Data
@Entity
/*表名*/
@Table(name = "account")
public class Account {
    /*配置主键生成策略-作用就是:自动生成主键的值*/
    @Id
    /*不写也可以运行*/
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;
    @Column(name = "name")
    private String username;
    @Column(name = "money")
    private Float money;
    @Column(name = "levelid")
    private String levelid;
}
```

dao层编写的模板

```java
/*	Account 实体类  String 主键类型
 *  JpaRepository<Account,String>,JpaSpecificationExecutor<Account>
 */
public interface AccountDao extends JpaRepository<Account, String>, JpaSpecificationExecutor<Account> {
    
}
```

