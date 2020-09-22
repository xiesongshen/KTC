package com.xss.ktc.client;

import com.xss.entity.Result;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @author XSS
 * @date 2020/8/26
 * @desc
 */
@FeignClient("ktc-user")  //调用user微服
public interface UserClient {

    /*
     * @author Xss
     * @date 2020/8/26
     * @param [userid, count]
     * @return Result
     * @description  更新关注数
     */
    @PutMapping(value = "/user/updateFollowCount/{userid}/{count}")
    public Result updateFollowCount(@PathVariable("userid") String userid,@PathVariable("count") Integer count);


    /*
     * @author Xss
     * @date 2020/8/26
     * @param [userid, count]
     * @return Result
     * @description  更新粉丝数
     */
    @PutMapping("/user/updateFansCount/{userid}/{count}")
    public Result updateFansCount(@PathVariable("userid") String userid,@PathVariable("count") Integer count);
}

