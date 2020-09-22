package com.ktc.qa.dao;

import com.ktc.qa.pojo.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @Description 问题 数据访问接口
 * @date 2020-08-19 16:28:12
*/
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{

    @Query("select p from Problem p where p.id in ( select pl.problemid from Pl pl where pl.labelid = ?1 ) order by p.reply desc")
    Page<Problem> findHotlistByLabelid(String labelid, Pageable pageable);

    @Query("select p from Problem p where p.id in ( select pl.problemid from Pl pl where pl.labelid = ?1 ) and p.reply=0 ")
    Page<Problem> findWaitlistByLabelid(String labelid, Pageable pageable);
}

