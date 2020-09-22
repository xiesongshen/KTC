package com.ktc.article.controller;

import com.ktc.article.pojo.Article;
import com.ktc.article.service.ArticleService;
import com.xss.entity.PageResult;
import com.xss.entity.Result;
import com.xss.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author admin
 * @Description 文章 控制器层
 * @date 2020-08-19 18:07:04
 */
@RestController
@CrossOrigin
@RequestMapping("/article")
@RefreshScope
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", articleService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", articleService.findById(id));
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
        Page<Article> pageList = articleService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Article>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", articleService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param article
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Article article) {
        articleService.add(article);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param article
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Article article, @PathVariable String id) {
        article.setId(id);
        articleService.update(article);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        articleService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 文章审核
     * @param articleid
     * @return
     */
    @RequestMapping(value="/examine/{articleid}",method= RequestMethod.PUT)
    public Result examine(@PathVariable String articleid){
        articleService.examine(articleid);
        return new Result(true,StatusCode.OK,"审核成功");
    }

    /**
     * 文章点赞
     *  thumbup自增1
     * @param articleid
     * @return
     */
    @RequestMapping(value = "/thumbup/{articleid}",method = RequestMethod.PUT)
    public Result thumbup(@PathVariable String articleid){
        articleService.thumbup(articleid);
        return new Result(true,StatusCode.OK,"点赞+1");
    }

}
