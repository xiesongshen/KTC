package com.xss.ktc.controller;

import com.xss.entity.Result;
import com.xss.entity.StatusCode;
import com.xss.ktc.client.UserClient;
import com.xss.ktc.service.FriendService;
import io.jsonwebtoken.Claims;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

/**
 * @author XSS
 * @date 2020/8/26
 * @desc
 */
@RequestMapping("friend")
@RestController
@CrossOrigin
@RefreshScope
public class FriendController {

    @Autowired
    private FriendService friendService;

    @Autowired
    private HttpServletRequest request;


    /*
     * @author Xss
     * @date 2020/8/26
     * @param [friendid]
     * @return com.xss.entity.Result
     * @description   加关注
     */
    @PutMapping("/like/{friendid}")
    public Result addFriend(@PathVariable String friendid) {
        Claims auth = (Claims) request.getAttribute("user_auth");
        if (auth == null) {
            return new Result(false, StatusCode.ERROR, "请先登录");
        }

        String userid = auth.getId();
        Boolean flag = friendService.addFriend(userid,friendid);

        if (flag) {
            return new Result(true, StatusCode.OK, "关注成功");
        } else {
            return new Result(true, StatusCode.OK, "取消关注成功");
        }

    }

    @DeleteMapping("/{friendid}")
    public Result deleteFriend(@PathVariable String friendid){
        Claims auth = (Claims) request.getAttribute("user_auth");

        if (auth == null) {
            return new Result(false, StatusCode.ERROR, "请先登录");
        }

        String userid = auth.getId();

        Boolean flag = friendService.addNoFriend(userid, friendid);

        if (flag) {
            return new Result(true, StatusCode.OK, "拉黑成功");
        } else {
            return new Result(true, StatusCode.OK, "您已经拉黑啦！");
        }
    }
}
