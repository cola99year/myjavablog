package com.cola.colablog.controller;

import com.cola.colablog.pojo.Article;
import com.cola.colablog.service.ArticleService;
import com.cola.colablog.vo.Result;
import com.cola.colablog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
