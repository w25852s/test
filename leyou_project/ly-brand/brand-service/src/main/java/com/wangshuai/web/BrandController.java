package com.wangshuai.web;

import com.github.pagehelper.PageInfo;
import com.wangshuai.bean.Brand;
import com.wangshuai.bean.PageRequest;
import com.wangshuai.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
//@RequestMapping("brand")
public class BrandController {

    @Autowired
    private IBrandService brandService;

    @GetMapping("page")
    public ResponseEntity<PageInfo<Brand>>  findPage(PageRequest pageRequest){
        System.out.println(pageRequest);
        if(pageRequest.getPage()==null)
            pageRequest.setPage(1);
        if(pageRequest.getRows()==null)
            pageRequest.setRows(5);
        List<Brand> brands = brandService.findPage(pageRequest);
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);
        return  ResponseEntity.ok(pageInfo);


    }

    @PostMapping
    public ResponseEntity<Void> saveBrand(Brand brand,@RequestParam(name="cids")List<Long> cids){
        brandService.saveBrand(brand, cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("cid/{cid}")
    public ResponseEntity<List<Brand>> findBrandBuCid(@PathVariable("cid") Long cid) {
        List<Brand> brands=brandService.findByCid(cid);
        return ResponseEntity.ok(brands);
    }

    @GetMapping("{id}")
    public ResponseEntity<Brand> findBrandById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(brandService.findByBid(id));

    }




}
