package com.cola.colablog.controller;

import com.cola.colablog.service.CommentService;
import com.cola.colablog.vo.Result;
import com.cola.colablog.vo.params.CommentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: cola99year
 * @Date: 2022/6/29 20:53
 */
@RestController
@RequestMapping("comments")
public class CommentController {
    @Autowired
    private CommentService commentsService;

    @GetMapping("article/{id}")
    public Result comments(@PathVariable("id") Long id){
        return commentsService.commentsByArticleId(id);
    }

    @PostMapping("create/change")
    public Result comment(@RequestBody CommentParam commentParam){
        return commentsService.comment(commentParam);
    }


}
