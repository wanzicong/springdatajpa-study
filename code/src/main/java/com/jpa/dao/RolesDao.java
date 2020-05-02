package com.jpa.dao;

import com.jpa.model.Account;
import com.jpa.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RolesDao extends JpaRepository<Roles, String>, JpaSpecificationExecutor<Roles> {
}
