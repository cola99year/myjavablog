package com.cola.colablog.controller;

import com.cola.colablog.common.aop.LogAnnotation;
import com.cola.colablog.pojo.Article;
import com.cola.colablog.service.ArticleService;
import com.cola.colablog.vo.Result;
import com.cola.colablog.vo.params.ArticleParam;
import com.cola.colablog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: cola99year
 * @Date: 2022/6/26 16:57
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    //首页展示所有文章列表
    @PostMapping
    //加上此注解 代表要对此接口记录日志
    @LogAnnotation(module="文章",operator="获取文章列表")
    public Result listAllArticle(@RequestBody PageParams pageParam){
        return articleService.listArticle(pageParam);
    }
    //最热文章，浏览数量最多的文章
    @PostMapping("hot")
    public Result hotArticle(){
        int limit = 5;
        return articleService.listHotArticle(limit);
    }
    //最新的文章=创建日期最新
    @PostMapping("new")
    public Result latestArticles(){
        int limit = 5;
        return articleService.newArticles(limit);
    }
    //文章按年份和月份分组
    @PostMapping("listArchives")
    public Result listArchives(){
        return articleService.listArchives();
    }
    //文章详情
    @PostMapping("view/{id}")
    //@Cache(expire = 5 * 60 * 1000,name = "view_article")
    public Result findArticleById(@PathVariable("id") Integer articleId){
        return articleService.findArticleById(articleId);
    }

    //接口url：/articles/publish
    //发布文章
    //请求方式：POST
    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam){

        return articleService.publish(articleParam);
    }
}
