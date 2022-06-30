package com.cola.colablog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cola.colablog.pojo.SysUser;
import com.cola.colablog.service.LoginService;
import com.cola.colablog.service.SysUserService;
import com.cola.colablog.utils.JWTUtils;
import com.cola.colablog.vo.ErrorCode;
import com.cola.colablog.vo.Result;
import com.cola.colablog.vo.params.LoginParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: cola99year
 * @Date: 2022/5/7 0:28
 */
@Service
@Transactional//事务注解,注册时需要，如果redis宕机，不能添加成功到数据库
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //MD5加密盐
    private static final String slat = "colablog!@#";

    @Override
    public Result login(LoginParam loginParam) {
        /**
         * 1. 检查参数是否合法
         * 2. 根据用户名和密码去user表中查询 是否存在
         * 3. 如果不存在 登录失败
         * 4. 如果存在 ，使用jwt 生成token 返回给前端
         * 5. token放入redis当中，redis  token：user信息 设置过期时间
         *  (登录认证的时候 先认证token字符串是否合法，去redis认证是否存在)
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        //账户或密码为空不合法
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        //MD5加密+盐加密后的password
        password = DigestUtils.md5Hex(password + slat);
        //System.out.println("我是密码"+password);
        //去数据库查找
        SysUser sysUser = sysUserService.findUser(account, password);
        //查询不到返回失败
        if (sysUser == null) {
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        //若存在，把用户的id传给JTW工具类，调用createToken方法生成token
        String token = JWTUtils.createToken(sysUser.getId());

        //key为"token"+token,value为sysUser
        //redis的String类型，调用fastjson把sysUser对象由JSON转成String，保存到redis。过期时间为1分钟
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 10, TimeUnit.MINUTES);
        return Result.success(token);
    }

    @Override
    public SysUser checkToken(String token) {
        //token若为空
        if (StringUtils.isBlank(token)) {
            return null;
        }
        //JWT解析是否成功？
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if (stringObjectMap == null) {
            return null;
        }
        //redis是否存在这个key？
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);

        if (StringUtils.isBlank(userJson)) {
            return null;
        }
        //redis的value保存有对象，不用去数据库查找。把String转成对象SysUser.class
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;
    }
    //退出登录把token删除
    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_" + token);
        return Result.success(null);
    }

    @Override
    public Result register(LoginParam loginParam) {
        /**
         * 1. 判断参数 是否合法
         * 2. 判断账户是否存在，存在 返回账户已经被注册
         * 3. 不存在，注册用户
         * 4. 生成token
         * 5. 存入redis 并返回
         * 6. 注意 加上事务，一旦中间的任何过程出现问题，注册的用户 需要回滚
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        String nickname = loginParam.getNickname();
        if (StringUtils.isBlank(account)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(nickname)
        ){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        //判断账户是否存在，存在，返回账户已经被注册信息
        SysUser sysUser =  sysUserService.findUserByAccount(account);
        if (sysUser != null){
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(),"账户已经被注册了");
        }
        //3. 不存在，注册用户
        sysUser = new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password+slat));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("/static/img/logo.b3a48c0.png");
        sysUser.setAdmin(0); //1 为true
        sysUser.setDeleted(0); // 0 为false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        this.sysUserService.save(sysUser);

        String token = JWTUtils.createToken(sysUser.getId());

        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.MINUTES);
        return Result.success(token);
    }
}