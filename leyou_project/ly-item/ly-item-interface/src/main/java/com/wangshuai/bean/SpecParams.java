package com.wangshuai.bean;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name="tb_spec_param")
public class SpecParams {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long  id;
    private Long cid;
    private Long groupId;
    private  String name;
    @Column(name="`numric`")
    private Boolean  numric;

    private String unit;

    private Boolean generic;

    private Boolean searching;

    private String segements;



}
