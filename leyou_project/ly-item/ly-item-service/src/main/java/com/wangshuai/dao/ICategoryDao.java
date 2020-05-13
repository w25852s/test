package com.wangshuai.dao;


import com.wangshuai.bean.Brand;
import com.wangshuai.bean.Category;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


public interface ICategoryDao extends Mapper<Category>, IdListMapper<Category,Long> {




}
