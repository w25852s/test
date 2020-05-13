package com.wangshuai.service;

import com.wangshuai.bean.SpecGroup;
import com.wangshuai.bean.SpecParams;

import java.util.List;

public interface ISpecificationService {


    List<SpecGroup> findSpecByCid(Integer cid);

    List<SpecParams> findParams(Long gid,Long cid,Boolean searching);

    void saveParam(SpecParams specParams);

    void saveSpecGroup(SpecGroup specGroup);
}
