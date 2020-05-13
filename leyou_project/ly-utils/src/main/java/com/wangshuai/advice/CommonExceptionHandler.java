package com.wangshuai.advice;

import com.wangshuai.bean.ExceptionBean;
import com.wangshuai.enum1.ExceptionEnum;
import com.wangshuai.exception.LyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ExceptionBean> handler(Throwable t) {
        if (t instanceof LyException) {
            LyException exception = (LyException) t;
            ExceptionEnum exceptionEnum = exception.getExceptionEnum();
            return ResponseEntity.status(exceptionEnum.getCode()).body(new ExceptionBean(exceptionEnum));
        }
        return null;
    }
}
