package com.example.demo.exception;

import com.example.demo.util.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: py
 * @create: 2020-01-02 14:28
 **/
@RestControllerAdvice
public class ExceptionController {
    private Logger log = LoggerFactory.getLogger(ExceptionController.class);


    @ExceptionHandler(Exception.class)
    public R method(Exception e) {
        log.error(""+e.getMessage(),e);
        return R.error("系统未知异常！！！"+e.getMessage());
    }

    @ExceptionHandler(ParamException.class)
    public R method(ParamException e) {
        return R.error(e.getCode(), e.getMsg());
    }
}
