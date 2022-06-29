package com.cola.colablog.service.impl;

import com.cola.colablog.mapper.CategoryMapper;
import com.cola.colablog.pojo.Category;
import com.cola.colablog.service.CategoryService;
import com.cola.colablog.vo.CategoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: cola99year
 * @Date: 2022/6/29 18:39
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public CategoryVo findCategoryById(Integer categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        CategoryVo categoryVo = new CategoryVo();
//        category,categoryVo两者所含字段相同，直接使用copyProperties
        BeanUtils.copyProperties(category,categoryVo);
        categoryVo.setId(String.valueOf(category.getId()));
        return categoryVo;
    }
}
