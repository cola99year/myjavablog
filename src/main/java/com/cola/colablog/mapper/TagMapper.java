package com.cola.colablog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cola.colablog.pojo.Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: cola99year
 * @Date: 2022/6/26 18:10
 */
@Repository
public interface TagMapper extends BaseMapper<Tag> {

    List<Tag> findTagsByArticleId(@Param("id") Integer id);
}
