package com.jpa.service.roles;

import com.jpa.dao.RolesDao;
import com.jpa.model.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindRoles {
    @Autowired
    RolesDao rolesDao;

    public List<Roles> findAll() {
        Pageable pageable = PageRequest.of(0, 3);
        Page<Roles> pages = rolesDao.findAll(pageable);
        return pages.getContent();
    }
}
