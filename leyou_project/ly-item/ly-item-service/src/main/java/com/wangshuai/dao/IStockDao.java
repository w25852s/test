package com.wangshuai.dao;

import com.wangshuai.bean.Stock;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface IStockDao  extends Mapper<Stock>, InsertListMapper<Stock> , IdListMapper<Stock,Long> {
}
