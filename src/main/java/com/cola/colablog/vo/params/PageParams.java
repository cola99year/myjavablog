package com.cola.colablog.vo.params;

import lombok.Data;

/**
 * @Author: cola99year
 * @Date: 2022/6/26 17:04
 */
@Data
public class PageParams {
    //定义好分页的数据
    //有@Data的原因，前端如果也写有这两个分页数据。
    //后端的controller方法里有@RequestBody会把前端传过来的参数解析，
    //调用@Data的set方法，把这两个参数重新设置
    private int page = 1;
    private int pageSize = 4;
}
