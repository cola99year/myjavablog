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
    //找出前5条最热的id
    List<Integer> selectHotTagsId(@Param("limit") Integer limit);
    //前5条最热的id，分别对应的标签名
    List<Tag> selectHotTags(@Param("hotTagsIds") List<Integer> hotTagsIds);
}
