package com.wangshuai.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@Table(name="tb_spu_detail")
public class SpuDetail {
    @Id
    private Long spuId;

    private String description;
    @Column(name="specifications")
    private String genericSpec;
    @Column(name="spec_template")
    private String specialSpec;

    private String packingList;

    private String afterService;




}
