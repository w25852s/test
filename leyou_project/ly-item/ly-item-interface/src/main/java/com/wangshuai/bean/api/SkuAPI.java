package com.wangshuai.bean.api;

import com.wangshuai.bean.Sku;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface SkuAPI {

    @GetMapping("sku/list")
    public List<Sku> findSkusById(@RequestParam("id") Long id) ;
}
