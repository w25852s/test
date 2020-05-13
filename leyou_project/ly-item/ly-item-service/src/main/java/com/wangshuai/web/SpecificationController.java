package com.wangshuai.web;

import com.wangshuai.bean.SpecGroup;
import com.wangshuai.bean.SpecParams;
import com.wangshuai.service.ISpecificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("spec")
public class SpecificationController {
    @Autowired
    private ISpecificationService specificationService;


    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> findSpecByCid(@PathVariable("cid") Integer cid) {
        return ResponseEntity.ok(specificationService.findSpecByCid(cid));

    }

    @GetMapping("params")
    public ResponseEntity<List<SpecParams>> findParamsByGid(@RequestParam(name = "gid",required = false) Long gid,
      @RequestParam(name="cid",required = false)Long cid,
        @RequestParam(name="searching",required = false)Boolean searching
    ) {
        return ResponseEntity.ok(specificationService.findParams(gid,cid,searching));

    }

    @PostMapping("param")
    public ResponseEntity<Void> saveParam(@RequestBody SpecParams specParams) {
        log.info(specParams.toString());
        specificationService.saveParam(specParams);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PostMapping("group")
    public ResponseEntity<Void> saveSpecGroup(@RequestBody SpecGroup specGroup) {
        log.info(specGroup.toString());
        specificationService.saveSpecGroup(specGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }


}
