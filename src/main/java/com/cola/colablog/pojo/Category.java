package com.cola.colablog.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Category {

    private Integer id;

    private String avatar;

    private String categoryName;

    private String description;
}
