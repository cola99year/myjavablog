package com.cola.colablog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: cola99year
 * @Date: 2022/6/26 16:58
 */
@Data
@AllArgsConstructor
public class Result {
    private boolean success;
    private Integer code;
    private String msg;
    private Object data;

    //封装返回成功，
    public static Result success(Object data) {
        return new Result(true,200,"success",data);
    }
    //错误码有许多的，先不写死
    public static Result fail(Integer code, String msg) {
        return new Result(false,code,msg,null);
    }
}
