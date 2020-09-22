package com.ktc.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ktc.user.pojo.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @Description 用户 数据访问接口
 * @date 2020-08-24 16:33:58
*/
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{

    User findByMobile(String mobile);

    /*
     * @author Xss
     * @date 2020/8/26
     * @param [userid, count]
     * @return void
     * @description  更新粉丝数
     */
    @Query("update User u set u.fanscount=u.fanscount+?2 where u.id=?1")
    @Modifying
    void updateFansCount(String userid, Integer count);

    /*
     * @author Xss
     * @date 2020/8/26
     * @param [userid, count]
     * @return void
     * @description  更新关注数
     */
    @Query("update User u set u.followcount=u.followcount+?2 where u.id=?1")
    @Modifying
    void updateFollowCount(String userid, Integer count);
}

