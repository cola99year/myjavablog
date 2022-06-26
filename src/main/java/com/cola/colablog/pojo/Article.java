package com.cola.colablog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 
 * @TableName cola_article
 */
@TableName(value ="cola_article")
@Data
@Component
public class Article implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 评论数量
     */
    private Integer commentCounts;

    /**
     * 创建时间
     */
    private Long createDate;

    /**
     * 简介
     */
    private String summary;

    /**
     * 标题
     */
    private String title;

    /**
     * 浏览数量
     */
    private Integer viewCounts;

    /**
     * 是否置顶
     */
    private Integer weight;

    /**
     * 作者id
     */
    private Integer authorId;

    /**
     * 内容id
     */
    private Integer bodyId;

    /**
     * 类别id
     */
    private Integer categoryId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", commentCounts=").append(commentCounts);
        sb.append(", createDate=").append(createDate);
        sb.append(", summary=").append(summary);
        sb.append(", title=").append(title);
        sb.append(", viewCounts=").append(viewCounts);
        sb.append(", weight=").append(weight);
        sb.append(", authorId=").append(authorId);
        sb.append(", bodyId=").append(bodyId);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}