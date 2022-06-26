package com.cola.colablog.service;

import com.cola.colablog.vo.TagVo;

import java.util.List;

/**
 * @Author: cola99year
 * @Date: 2022/6/26 18:13
 */
public interface TagService {
    List<TagVo> findTagsByArticleId(Integer id);
}
