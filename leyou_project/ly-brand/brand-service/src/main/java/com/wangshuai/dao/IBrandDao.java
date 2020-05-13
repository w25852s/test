package com.wangshuai.dao;

import com.wangshuai.bean.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface IBrandDao extends Mapper<Brand> {


    @Insert("insert into tb_category_brand values(#{cid},#{bid})")
    Integer saveBrand(@Param("cid")Long cid,@Param("bid") Long bid);

    @Select("select a.* " +
            " from tb_brand a " +
            "       inner join" +
            "     tb_category_brand c on a.id = c.brand_id" +
            " where c.category_id = #{cid}")
    List<Brand>  findBrandByCid(Long cid);
}
