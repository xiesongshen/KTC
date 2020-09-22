package com.xss.recruit.dao;


import com.xss.recruit.pojo.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @Description 职位 数据访问接口
 * @date 2020-08-19 15:54:38
*/
public interface RecruitDao extends JpaRepository<Recruit,String>, JpaSpecificationExecutor<Recruit> {

    List<Recruit> findTop4ByStateOrderByCreatetimeDesc(String state);

    List<Recruit> findTop12ByStateNotOrderByCreatetimeDesc(String state);
}

