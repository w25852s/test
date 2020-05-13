package com.wangshuai.dao;

import com.wangshuai.bean.Sku;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface ISkuDao  extends Mapper<Sku>, IdListMapper<Sku,Long> {
}
