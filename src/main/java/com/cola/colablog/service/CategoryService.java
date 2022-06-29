package com.cola.colablog.service;

import com.cola.colablog.vo.CategoryVo;

/**
 * @Author: cola99year
 * @Date: 2022/6/29 18:39
 */
public interface CategoryService {
    CategoryVo findCategoryById(Integer categoryId);
}
