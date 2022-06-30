package com.cola.colablog.vo;

import lombok.Data;

/**
 * @Author: cola99year
 * @Date: 2022/6/26 17:16
 */
@Data
public class TagVo {
    //因为tag实体类有头像，因此也封装一份vo层
    private Integer id;
    private String tagName;
}
