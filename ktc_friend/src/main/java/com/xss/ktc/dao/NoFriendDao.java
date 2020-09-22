package com.xss.ktc.dao;

import com.xss.ktc.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author XSS
 * @date 2020/8/26
 * @desc
 */
public interface NoFriendDao extends JpaRepository<NoFriend,String> {

    Integer countByUseridAndFriendid(String userid, String friendid);
}
