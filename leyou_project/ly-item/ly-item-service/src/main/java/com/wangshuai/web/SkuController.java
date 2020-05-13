package com.wangshuai.web;

import com.wangshuai.bean.Sku;
import com.wangshuai.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sku")
public class SkuController {

    @Autowired
    private IGoodsService goodsService;

    @GetMapping("list")
    public ResponseEntity<List<Sku>> findSkusById(@RequestParam("id") Long id) {
        return ResponseEntity.ok(goodsService.findSkusById(id));


    }
}
