package com.wangshuai.bean.api;

import com.github.pagehelper.PageInfo;
import com.wangshuai.bean.Spu;
import com.wangshuai.bean.SpuDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface SpuAPI {
    @GetMapping("spu/page")
    public PageInfo<Spu> findByPage(
            @RequestParam(name = "key", defaultValue = "") String key,
            @RequestParam(name = "saleable", required = false) Boolean saleable,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "rows", defaultValue = "5") Integer rows
    );

    @GetMapping("spu/detail/{id}")
    public SpuDetail getDetailById(@PathVariable("id") Long id) ;

}
