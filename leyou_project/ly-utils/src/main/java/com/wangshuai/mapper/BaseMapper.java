package com.wangshuai.mapper;

import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;

public interface BaseMapper<T>  extends IdListMapper<T,Long>, InsertListMapper<T> {
}
