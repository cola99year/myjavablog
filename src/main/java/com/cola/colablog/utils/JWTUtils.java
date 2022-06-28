package com.cola.colablog.utils;

/**
 * @Author: cola99year
 * @Date: 2022/5/6 23:57
 */
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
//import org.jasypt.util.text.BasicTextEncryptor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {

    private static final String jwtToken = "123456colablog!@#$$";

    //生成token
    public static String createToken(Integer userId){
        Map<String,Object> claims = new HashMap<>();
        //body部分的数据，头+body+密钥生成jwt签证
        claims.put("userId",userId);
        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, jwtToken) // 签发算法，秘钥为jwtToken
                .setClaims(claims) // body数据，要唯一，自行设置
                .setIssuedAt(new Date()) // 设置签发时间
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000));// 一天的有效时间
        String token = jwtBuilder.compact();
        return token;
    }

    /**
     * 检查token
     * @param token 前端请求头发来的
     * @return
     */
    public static Map<String, Object> checkToken(String token){
        try {
            //拿到密钥来解析token，解析成功返回
            Jwt parse = Jwts.parser().setSigningKey(jwtToken).parse(token);
            //只是拿body部分存入map
            return (Map<String, Object>) parse.getBody();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

    public static void main(String[] args) {
        //BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        ////加密所需的salt
        //textEncryptor.setPassword("mszlu_blog_$#@wzb_&^%$#");
        ////要加密的数据（数据库的用户名或密码）
        //String username = textEncryptor.encrypt("root");
        //String password = textEncryptor.encrypt("123456");
        //System.out.println("username:"+username);
        //System.out.println("password:"+password);
        //System.out.println(textEncryptor.decrypt("29cZ+X9cNmECjbLXT2P/BBZWReVl30NS"));
    }

}