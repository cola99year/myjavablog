package com.cola.colablog.service;

import com.cola.colablog.vo.CategoryVo;
import com.cola.colablog.vo.Result;

/**
 * @Author: cola99year
 * @Date: 2022/6/29 18:39
 */
public interface CategoryService {
    CategoryVo findCategoryById(Integer categoryId);

    Result findAll();

    Result findAllDetail();

    Result categoryDetailById(Long id);
}
