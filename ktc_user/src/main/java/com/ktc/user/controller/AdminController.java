package com.ktc.user.controller;

import com.ktc.user.pojo.Admin;
import com.ktc.user.service.AdminService;
import com.xss.entity.PageResult;
import com.xss.entity.Result;
import com.xss.entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import util.JwtUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author admin
 * @Description 管理员 控制器层
 * @date 2020-08-24 16:33:29
 */
@RestController
@CrossOrigin
@RequestMapping("/admin")
@RefreshScope
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private Environment env;

    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", adminService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", adminService.findById(id));
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
        Page<Admin> pageList = adminService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Admin>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", adminService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param admin
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Admin admin) {
        adminService.add(admin);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param admin
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Admin admin, @PathVariable String id) {
        admin.setId(id);
        adminService.update(admin);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id, HttpServletRequest request) {

        String auth = request.getHeader("auth");
        if (auth==null){
            return new Result(false,StatusCode.ACCESS_ERROR,"请先认证");

        }

        String prefix = env.getProperty("jwt.config.prefix");

        if (!auth.startsWith(prefix)){
            return new Result(false, StatusCode.ACCESS_ERROR, "认证有误");
        }

        String token = auth.substring(prefix.length());

        Claims claims = jwtUtil.parseJWT(token);
        if (claims.get("role").equals("admin")){
            return new Result(false, StatusCode.ACCESS_ERROR, "权限不足");
        }

        adminService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /*
     * @author Xss
     * @date 2020/8/25
     * @param [admin]
     * @return com.xss.entity.Result
     * @description  管理员登录密码校验
     */
    @PostMapping("/login")
    public Result login(@RequestBody Admin admin) {
        Admin dbAdmin = adminService.login(admin.getLoginname(), admin.getPassword());

        if (dbAdmin != null) {
            //创建token准备写到前端
            String token = jwtUtil.createJWT(dbAdmin.getId(), dbAdmin.getLoginname(), "admin");

            HashMap<String, String> returnMap = new HashMap<>();
            returnMap.put("name",dbAdmin.getLoginname());
            returnMap.put("token",token);


            return new Result(true, StatusCode.OK, "登陆成功",returnMap);
        } else {
            return new Result(false, StatusCode.USER_PASSWORD_ERROR, "用户名或密码错误");
        }
    }

}
