package com.cola.colablog.vo.params;

import lombok.Data;

@Data
public class CommentParam {

    private Integer articleId;

    private String content;

    private Integer parent;

    private Integer toUserId;
}
