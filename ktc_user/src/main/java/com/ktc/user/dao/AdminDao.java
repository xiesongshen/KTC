package com.ktc.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ktc.user.pojo.Admin;

/**
 * @Description 管理员 数据访问接口
 * @date 2020-08-24 16:33:28
*/
public interface AdminDao extends JpaRepository<Admin,String>,JpaSpecificationExecutor<Admin>{

    Admin findByLoginname(String loginname);
}

