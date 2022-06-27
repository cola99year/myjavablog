package com.cola.colablog.vo.params;

/**
 * @Author: cola99year
 * @Date: 2022/5/7 0:26
 */
import lombok.Data;

@Data
public class LoginParam {

    private String account;

    private String password;

//    这个是注册用
    private String nickname;
}
