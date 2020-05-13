package com.wangshuai.web;

import com.github.pagehelper.PageInfo;
import com.wangshuai.bean.Spu;
import com.wangshuai.bean.SpuDetail;
import com.wangshuai.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("spu")
public class SpuController {

    @Autowired
    private IGoodsService goodsService;

    @GetMapping("page")
    public ResponseEntity<PageInfo<Spu>>  findByPage(
            @RequestParam(name="key",defaultValue = "")String key,
            @RequestParam(name="saleable",required = false)Boolean saleable,
            @RequestParam(name="page",defaultValue = "1")Integer page,
            @RequestParam(name="rows",defaultValue = "5")Integer rows
    ){
      List<Spu> spus= goodsService.findByPage(key, saleable, page, rows);
        PageInfo info = new PageInfo(spus);
        return ResponseEntity.ok(info);
    }

    @GetMapping("detail/{id}")
    public ResponseEntity<SpuDetail> getDetailById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(goodsService.findDetailById(id));
    }
}
