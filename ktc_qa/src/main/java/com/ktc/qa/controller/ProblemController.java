package com.ktc.qa.controller;

import com.ktc.qa.client.LabelClient;
import com.ktc.qa.pojo.Problem;
import com.ktc.qa.service.ProblemService;
import com.xss.entity.PageResult;
import com.xss.entity.Result;
import com.xss.entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * @author admin
 * @Description 问题 控制器层
 * @date 2020-08-19 16:28:12
 */
@RestController
@CrossOrigin
@RequestMapping("/problem")
@RefreshScope
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @Autowired
    private LabelClient labelClient;

    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findById(id));
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
        Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param problem
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Problem problem, HttpServletRequest request) {

        Claims claims = (Claims) request.getAttribute("user_auth");

        if (claims != null && claims.get("role").equals("user")) {
            problemService.add(problem);
            return new Result(true, StatusCode.OK, "增加成功");
        }

        return new Result(false, StatusCode.ACCESS_ERROR, "权限不足");
    }

    /**
     * 修改
     *
     * @param problem
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Problem problem, @PathVariable String id) {
        problem.setId(id);
        problemService.update(problem);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        problemService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 热门问答列表+分页
     * 需求：查询问题表中指定标签下的所属问题,按照回复数倒序排列
     *
     * @param labelid
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/hotlist/{labelid}/{page}/{size}", method = RequestMethod.GET)
    public Result hotlist(@PathVariable String labelid, @PathVariable int page, @PathVariable int size) {
        Page<Problem> pageData = problemService.hotlist(labelid, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageData.getTotalElements(), pageData.getContent()));
    }

    /**
     * 等待问答列表+分页
     * 需求：查询问题表中指定标签下的所属问题,回复数为0的即为等待回答
     *
     * @param labelid
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/waitlist/{labelid}/{page}/{size}", method = RequestMethod.GET)
    public Result waitlist
    (@PathVariable String labelid, @PathVariable int page, @PathVariable int size) {

        Page<Problem> pageData = problemService.waitlist(labelid, page, size);

        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageData.getTotalElements(), pageData.getContent()));
    }

    /*
     * @author Xss
     * @date 2020/8/26
     * @param [labelid]
     * @return com.xss.entity.Result
     * @description
     */
    @GetMapping("/label/{labelid}")
    public Result findLabelByLabelId(@PathVariable String labelid){
        Result result = labelClient.findById(labelid);

        return result;
    }
}
