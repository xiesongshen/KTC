package com.xss.controller;

import com.xss.entity.PageResult;
import com.xss.entity.Result;
import com.xss.entity.StatusCode;
import com.xss.pojo.Label;
import com.xss.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author XSS
 * @date 2020/8/18
 * @desc
 */
@RestController
@RequestMapping("label")
@CrossOrigin
@RefreshScope // 刷新自定义配置
public class LabelController {

    @Autowired
    LabelService service;

    @Value("${dfbz.config.info}")
    private String info;


    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Label label){
        service.add(label);

        return new Result(true, StatusCode.OK,"添加成功");
    }

    /**
     * 根据id删除标签
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result deleteByID(@PathVariable String id) {
        service.deleteById(id);

        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 修改标签
     * @param id
     * @param label
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable String id, @RequestBody Label label) {
        label.setId(id);
        service.update(label);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 根据标签id查询单个标签
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        Label label= service.findById(id);

        return new Result(true, StatusCode.OK, "查询成功", label);
    }


    /**
     * 查询全部标签
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        List<Label> labelList = service.findAll();

        return new Result(true, StatusCode.OK, "查询成功", labelList);
    }

    /*
     * @author Xss
     * @date 2020/8/19
     * @param [searchMap]
     * @return com.xss.entity.Result
     * @description  根据条件查询标签信息
     */
    @PostMapping("search")
    public Result search(@RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",service.search(searchMap));
    }

    /*
     * @author Xss
     * @date 2020/8/19
     * @param [searchMap, page, size]
     * @return com.xss.entity.Result
     * @description  条件+查询分页信息
     */
    @PostMapping("search/{page}/{size}")
    public Result FindByPage(@RequestBody Map searchMap,Integer page,Integer size){
        Page<Label> pageData=service.findPage(searchMap,page,size);

        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageData.getTotalElements(),pageData.getContent()));
    }

    @RequestMapping(value = "/testBus", method = RequestMethod.GET)
    public Result testBus() {

        return new Result(true, StatusCode.OK, "查询成功", info);
    }

}
