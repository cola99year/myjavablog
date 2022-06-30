package com.cola.colablog.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ArticleTag {

    private Integer id;

    private Integer articleId;

    private Integer tagId;
}
