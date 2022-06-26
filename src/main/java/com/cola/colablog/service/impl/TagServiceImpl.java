package com.cola.colablog.service.impl;

import com.cola.colablog.mapper.TagMapper;
import com.cola.colablog.pojo.Tag;
import com.cola.colablog.service.TagService;
import com.cola.colablog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
