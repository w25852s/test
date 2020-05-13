package com.wangshuai.bean.api;

import com.wangshuai.bean.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CategoryAPI {

    @GetMapping("category/list/cids")
    public List<Category> findByCids(@RequestParam("cids") List<Long> cids) ;



}
