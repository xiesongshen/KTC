package com.xss.recruit.dao;

import com.xss.recruit.pojo.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @Description 企业 数据访问接口
 * @date 2020-08-19 15:27:19
*/
public interface EnterpriseDao extends JpaRepository<Enterprise,String>, JpaSpecificationExecutor<Enterprise> {

    /**
     *	查询热门企业
     */
    public List<Enterprise> findTop12ByIshotEquals(String ishot);
}

