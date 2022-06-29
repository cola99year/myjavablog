package com.cola.colablog.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ArticleBody {

    private Integer id;
    private String content;
    private String contentHtml;
    private Integer articleId;
}
