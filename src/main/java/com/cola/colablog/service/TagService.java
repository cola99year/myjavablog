package com.cola.colablog.service;

import com.cola.colablog.pojo.Tag;
import com.cola.colablog.vo.Result;
import com.cola.colablog.vo.TagVo;

import java.util.List;

/**
 * @Author: cola99year
 * @Date: 2022/6/26 18:13
 */
public interface TagService {
    List<TagVo> findTagsByArticleId(Integer id);
    Result hotTag(Integer limit);

    Result findAll();

    Result findAllDetail();

    Result findDetailById(Long id);
}
