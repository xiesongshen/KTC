package com.xss.ktc.service;

import com.xss.ktc.client.UserClient;
import com.xss.ktc.dao.FriendDao;
import com.xss.ktc.dao.NoFriendDao;
import com.xss.ktc.pojo.Friend;
import com.xss.ktc.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author XSS
 * @date 2020/8/26
 * @desc
 */
@Service
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    @Autowired
    private UserClient userClient;

    @Transactional
    public Boolean addFriend(String userid, String friendid) {
        if (friendDao.countFriendByUseridAndFriendid(userid, friendid) > 0) {

            //"我"原来就关注过对方(取消关注)
            friendDao.deleteByUseridAndFriendid(userid, friendid);

            // 对方原来是否关注过"我"
            if (friendDao.countFriendByUseridAndFriendid(friendid, userid) > 0) {
                friendDao.updateIslike(friendid, userid, "0");
            }

            // 对方的粉丝数-1
            userClient.updateFansCount(friendid, -1);

            // 我的关注数-1
            userClient.updateFollowCount(userid, -1);

            return false;

        }else {
            //"我"第一次关注对方
            Friend friend = new Friend();
            friend.setUserid(userid);
            friend.setFriendid(friendid);
            friend.setIslike("0");		// 先默认为0

            // 对方原来是否关注过"我"
            if (friendDao.countFriendByUseridAndFriendid(friendid, userid) > 0) {

                friend.setIslike("1");

                friendDao.updateIslike(friendid, userid, "1");
            }

            friendDao.save(friend);


            // 对方的粉丝数+1
            userClient.updateFansCount(friendid, 1);

            //我的关注数+1
            userClient.updateFollowCount(userid, 1);

            return true;
        }
    }

    /*
     * @author Xss
     * @date 2020/8/26
     * @param [userid, friendid]
     * @return java.lang.Boolean
     * @description  拉黑好友
     */
    @Transactional
    public Boolean addNoFriend(String userid, String friendid) {

        // 首先是否已经拉黑过
        if (noFriendDao.countByUseridAndFriendid(userid, friendid) > 0) {

            //已经拉黑过
            return false;
        } else {

            // 还没有拉黑
            NoFriend noFriend = new NoFriend();
            noFriend.setUserid(userid);
            noFriend.setFriendid(friendid);

            noFriendDao.save(noFriend);

            return true;
        }

    }
}
