package com.cola.colablog.handler;

import com.cola.colablog.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: cola99year
 * @Date: 2022/6/27 18:25
 */
//对加了@Controller注解的方法，进行拦截处理 AOP的实现
@ControllerAdvice
public class AllExceptionHandler {
    //进行异常处理，处理Exception.class的异常。处理你系统中未知的bug
    @ExceptionHandler(Exception.class)
    @ResponseBody //返回json数据，因为错误是要返回给前端的
    public Result doException(Exception ex){
        ex.printStackTrace();
        return Result.fail(-999,"系统出现未知bug");
    }

}
