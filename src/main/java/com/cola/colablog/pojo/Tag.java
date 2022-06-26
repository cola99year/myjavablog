package com.cola.colablog.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Author: cola99year
 * @Date: 2022/6/26 18:06
 */
@Component
@Data
public class Tag {
    private Integer id;
    private String tagName;
    //标签头像
    private String avatar;
}
