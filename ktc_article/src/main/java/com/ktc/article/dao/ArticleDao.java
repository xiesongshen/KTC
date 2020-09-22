package com.ktc.article.dao;

import com.ktc.article.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @Description 文章 数据访问接口
 * @date 2020-08-19 18:07:04
*/
public interface ArticleDao extends JpaRepository<Article,String>,JpaSpecificationExecutor<Article>{

    @Modifying      //如果是使用Query进行更新操作必须加上@Midifying注解
    @Query("update Article a set a.state=1 where a.id=?1")
    void examine(String id);

    @Modifying
    @Query("update Article a set a.thumbup=a.thumbup+1 where a.id=?1")
    void thumbup(String id);
}

