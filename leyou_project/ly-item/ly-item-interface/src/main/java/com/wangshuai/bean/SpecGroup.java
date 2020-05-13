package com.wangshuai.bean;


import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name="tb_spec_group")
public class SpecGroup {
@Id
@KeySql(useGeneratedKeys = true)
    private Long id ;

    private String name;

    private Long cid;


}
