package com.cola.colablog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cola.colablog.mapper.TagMapper;
import com.cola.colablog.pojo.Tag;
import com.cola.colablog.service.TagService;
import com.cola.colablog.vo.Result;
import com.cola.colablog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: cola99year
 * @Date: 2022/6/26 18:14
 */
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<TagVo> findTagsByArticleId(Integer id) {
        List<Tag> tags = tagMapper.findTagsByArticleId(id);
        List<TagVo> tagVoList = copyList(tags);
        return tagVoList;
    }


    //最火标签=该标签含有的文章最多
    @Override
    public Result hotTag(Integer limit){
        //找出前5条最热的id,返回list集合传给下面
        List<Integer> hotTagsIds = tagMapper.selectHotTagsId(limit);
        //前5条最热的id，分别对应的标签名
        List<Tag> hotTagsName = tagMapper.selectHotTags(hotTagsIds);
        return Result.success(hotTagsName);
    }

    @Override
    public Result findAll() {
        List<Tag> tags = tagMapper.selectList(null);
        return Result.success(copyList(tags));
    }

    @Override
    public Result findAllDetail() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        List<Tag> tags = this.tagMapper.selectList(queryWrapper);
        return Result.success(copyList(tags));
    }

    @Override
    public Result findDetailById(Long id) {
        Tag tag = tagMapper.selectById(id);
        return Result.success(copy(tag));
    }



    private List<TagVo> copyList(List<Tag> tags){
        List<TagVo> tagVoList = new ArrayList<>();
        for(Tag tag : tags){
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }
    private TagVo copy(Tag tag){
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }


}
