package com.cola.colablog.vo.params;

import com.cola.colablog.vo.CategoryVo;
import com.cola.colablog.vo.TagVo;
import lombok.Data;

import java.util.List;

@Data
public class ArticleParam {

    private Integer id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;

    private String title;

    private String search;
}
