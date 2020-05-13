package com.wangshuai.service;

import com.wangshuai.bean.Sku;
import com.wangshuai.bean.Spu;
import com.wangshuai.bean.SpuDetail;

import java.util.List;

public interface IGoodsService {
     List<Spu> findByPage(String key, Boolean saleable, Integer page, Integer rows) ;

     void savGood(Spu spu);

    SpuDetail findDetailById(Long id);

    List<Sku> findSkusById(Long id);
}
