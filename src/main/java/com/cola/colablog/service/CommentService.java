package com.cola.colablog.service;

import com.cola.colablog.vo.Result;

/**
 * @Author: cola99year
 * @Date: 2022/6/29 20:52
 */
public interface CommentService {
    Result commentsByArticleId(Long id);
}
