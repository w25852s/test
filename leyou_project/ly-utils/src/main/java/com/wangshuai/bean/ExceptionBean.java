package com.wangshuai.bean;

import com.wangshuai.enum1.ExceptionEnum;
import lombok.Data;

@Data
public class ExceptionBean {
    private int status;
    private String errormessage;
    private Long timestamp;

    public ExceptionBean(ExceptionEnum em) {
        this.status = em.getCode();
        this.errormessage = em.getMessage();
        this.timestamp = System.currentTimeMillis();
    }
}
