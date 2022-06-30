package com.cola.colablog.service;

import com.cola.colablog.vo.Result;
import com.cola.colablog.vo.params.CommentParam;

/**
 * @Author: cola99year
 * @Date: 2022/6/29 20:52
 */
public interface CommentService {
    Result commentsByArticleId(Long id);

    Result comment(CommentParam commentParam);
}
