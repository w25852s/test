package com.wangshuai.service;




import com.wangshuai.bean.Category;

import java.util.List;

public interface ICategoryService {

    List<Category> findCategoryByPid(Long pid);

    List<Category> findByCids(List<Long> cids);

    List<Category> findByParams(Long gid, Long cid, Boolean searching);
}
