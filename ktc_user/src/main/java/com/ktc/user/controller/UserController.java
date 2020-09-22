package com.ktc.user.controller;

import com.ktc.user.pojo.User;
import com.ktc.user.service.UserService;
import com.xss.entity.PageResult;
import com.xss.entity.Result;
import com.xss.entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import util.JwtUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author admin
 * @Description 用户 控制器层
 * @date 2020-08-24 16:33:58
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
@RefreshScope
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", userService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", userService.findById(id));
    }

    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<User> pageList = userService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<User>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", userService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param user
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody User user) {
        userService.add(user);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param user
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody User user, @PathVariable String id) {
        user.setId(id);
        userService.update(user);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id, HttpServletRequest request) {
        Claims admin_auth = (Claims) request.getAttribute("admin_auth");

        if (admin_auth == null) {
            return new Result(false, StatusCode.ACCESS_ERROR, "无权访问");
        }

        userService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /*
     * @author Xss
     * @date 2020/8/24
     * @param [mobile]
     * @return com.xss.entity.Result
     * @description 发送验证码
     */
    @PostMapping("/sendsms/{mobile}")
    public Result sendSms(@PathVariable String mobile) {
        userService.sendSms(mobile);
        return new Result(true, StatusCode.OK, "短信发送成功");
    }


    /*
     * @author Xss
     * @date 2020/8/24
     * @param [code]
     * @return com.xss.entity.Result
     * @description 用户注册
     */
    @PostMapping("/register/{code}")
    public Result register(@RequestBody User user, @PathVariable String code) {
        Result result = userService.register(user, code);

        return result;
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        User dbUser = userService.login(user.getMobile(), user.getPassword());

        if (dbUser != null) {
            String token = jwtUtil.createJWT(dbUser.getId(), dbUser.getNickname(), "user");

            HashMap<String, String> returnMap = new HashMap<>();
            returnMap.put("token", token);
            returnMap.put("nickname", dbUser.getNickname());
            returnMap.put("avatar", dbUser.getAvatar());

            return new Result(true, StatusCode.OK, "登陆成功",returnMap);

        }
        return new Result(false, StatusCode.USER_PASSWORD_ERROR, "用户名或密码错误");
    }

    /*
     * @author Xss
     * @date 2020/8/26
     * @param [userid, count]
     * @return com.xss.entity.Result
     * @description  更新粉丝数
     */
    @PutMapping("/updateFansCount/{userid}/{count}")
    public Result updateFansCount(@PathVariable String userid,@PathVariable Integer count) {
        userService.updateFansCount(userid, count);

        return new Result(true,StatusCode.OK,"更新成功");
    }

    /*
     * @author Xss
     * @date 2020/8/26
     * @param [userid, count]
     * @return com.xss.entity.Result
     * @description  更新关注数
     */
    @PutMapping(value = "/updateFollowCount/{userid}/{count}")
    public Result updateFollowCount(@PathVariable String userid,@PathVariable Integer count) {
        userService.updateFollowCount(userid,count);

        return new Result(true,StatusCode.OK,"更新成功");
    }


}
