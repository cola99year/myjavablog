package com.cola.colablog.controller;

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

    @PostMapping
    public Result listAllArticle(@RequestBody PageParams pageParam){
        return articleService.listArticle(pageParam);
    }
}
