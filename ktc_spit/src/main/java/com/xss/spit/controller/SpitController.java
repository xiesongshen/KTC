package com.xss.spit.controller;

import com.xss.entity.PageResult;
import com.xss.entity.Result;
import com.xss.entity.StatusCode;
import com.xss.spit.pojo.Spit;
import com.xss.spit.service.SpitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @author XSS
 * @date 2020/8/20
 * @desc
 */
@RestController
@RequestMapping("/spit")
@CrossOrigin
@RefreshScope
public class SpitController {

    @Autowired
    private SpitService spitService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询全部
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", spitService.findAll());
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", spitService.findById(id));
    }

    /**
     * 修改
     *
     * @param spit
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@PathVariable String id, @RequestBody Spit spit) {
        spit.setId(id);
        spitService.update(spit);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@RequestBody String id) {
        spitService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 增加
     *
     * @param spit
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Spit spit) {
        spitService.save(spit);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @GetMapping("/comment/{parentid}/{page}/{size}")
    public Result comment(@PathVariable String parentid, @PathVariable int page, @PathVariable int size) {
        Page<Spit> pageData = spitService.comment(parentid, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageData.getTotalElements(), pageData.getContent()));
    }

    /**
     * 吐槽点赞
     * thumbup+1
     *
     * @param spitid
     * @return
     */
    @RequestMapping(value = "/thumbup/{spitid}", method = RequestMethod.PUT)
    public Result thumbup(@PathVariable String spitid) {
        // 登录用户的id(暂时写死)
        String userid = "3";

        Object flag = redisTemplate.opsForValue().get("spit:" + userid + ":" + spitid);

        if (flag != null) {

            // 删除点赞标识符
            redisTemplate.delete("spit:" + userid + ":" + spitid);

            // 取消点赞
            spitService.delThumbup(spitid);

            return new Result(true, StatusCode.OK, "取消点赞成功");
        }

        // 点赞的
        redisTemplate.opsForValue().set("spit:" + userid + ":" + spitid, 0);

        // 点赞生效
        spitService.thumbup(spitid);

        return new Result(true, StatusCode.OK, "点赞成功");
    }

}

