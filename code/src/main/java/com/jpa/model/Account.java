package com.jpa.model;

import lombok.Data;

import javax.persistence.*;

/*这是一个完整的配置注解*/
@Data
@Entity
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
