package com.wangshuai.service;

import com.wangshuai.bean.Brand;
import com.wangshuai.bean.PageRequest;
import com.wangshuai.exception.LyException;

import java.util.List;

public interface IBrandService {
    List<Brand> findPage(PageRequest pageRequest) throws LyException;

    void saveBrand(Brand brand, List<Long> cids);

    Brand findByBid(Long bid);

    List<Brand> findByCid(Long cid);
}
