package com.cola.colablog.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author: cola99year
 * @Date: 2022/6/26 17:14
 */
@Data
public class ArticleVo {

    private String id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    private Integer weight;

    //创建时间
    private String createDate;

    private String author;
    //实体类没有标签的属性，vo层可以封装，多表查询得到。
    private List<TagVo> tags;
}
