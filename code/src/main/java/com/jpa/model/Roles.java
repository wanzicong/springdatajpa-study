package com.jpa.model;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "roles")
@Data
public class Roles {
    @Id
    @Column(name = "levelid")
    private String levelid;
    @Column(name = "name")
    private String levelname;
}
