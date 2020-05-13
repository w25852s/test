package com.wangshuai.exception;

import com.wangshuai.enum1.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LyException extends  RuntimeException {
    private ExceptionEnum exceptionEnum;
}
