package com.cola.colablog.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Author: cola99year
 * @Date: 2022/6/26 18:08
 */
@Data
@Component
public class SysUser {
    private Integer id;

    private String account;

    private Integer admin;

    private String avatar;

    private Long createDate;

    private Integer deleted;

    private String email;

    private Long lastLogin;

    private String mobilePhoneNumber;

    private String nickname;

    private String password;

    private String salt;

    private String status;
}
