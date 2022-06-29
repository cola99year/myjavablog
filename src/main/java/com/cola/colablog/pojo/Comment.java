package com.cola.colablog.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Comment {

    private Integer id;

    private String content;

    private Long createDate;

    private Integer articleId;

    private Integer authorId;

    private Integer parentId;

    private Integer toUid;

    private Integer level;
}
