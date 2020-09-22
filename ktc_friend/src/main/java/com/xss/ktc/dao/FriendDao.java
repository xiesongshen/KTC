package com.xss.ktc.dao;

import com.xss.ktc.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author XSS
 * @date 2020/8/26
 * @desc
 */
public interface FriendDao extends JpaRepository<Friend,String> {

    Integer countFriendByUseridAndFriendid(String userid, String friendid);

    @Query("update Friend f set f.islike=?3 where f.userid=?1 and f.friendid=?2")
    @Modifying
    void deleteByUseridAndFriendid(String userid, String friendid);


    @Query("delete from Friend f where f.userid=?1 and f.friendid=?2")
    @Modifying
    void updateIslike(String friendid, String userid, String s);
}
