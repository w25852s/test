package com.wangshuai.web;

import com.netflix.discovery.converters.Auto;
import com.wangshuai.bean.Spu;
import com.wangshuai.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("goods")
public class GoodController {

    @Autowired
    private IGoodsService goodsService;


    @PostMapping
    public ResponseEntity<Void> saveGood(@RequestBody Spu spu) {
        goodsService.savGood(spu);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }
}
