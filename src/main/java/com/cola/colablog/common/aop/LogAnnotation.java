package com.cola.colablog.common.aop;

import java.lang.annotation.*;

//Type 代表可以放在类上面
// Method 代表可以放在方法上
@Target({ElementType.METHOD})
//runtime表示jvm层面的注解
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

//自定义两个参数，使用自己的注解时，就要给这两个参数赋值
    String module() default "";
    String operator() default "";
}
