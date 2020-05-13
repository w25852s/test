package com.wangshuai.api;

import com.wangshuai.bean.Brand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface BrandAPI {
    @GetMapping("{id}")
    public Brand findBrandById(@PathVariable("id") Long id) ;

}
