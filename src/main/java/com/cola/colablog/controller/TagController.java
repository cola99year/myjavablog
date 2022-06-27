package com.cola.colablog.controller;

import com.cola.colablog.service.TagService;
import com.cola.colablog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: cola99year
 * @Date: 2022/6/27 18:07
 */
@RestController
@RequestMapping("/tags")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("hot")
    public Result hot(){
        int limit = 5;
        return tagService.hotTag(limit);
    }
}
