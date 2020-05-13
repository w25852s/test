package com.wangshuai.bean;

import lombok.Data;

@Data
public class PageRequest {

    private String key;

    private Integer page;

    private Integer rows;

    private boolean des;

    private String sortBy;


}
