package com.wangshuai.web;



import com.wangshuai.bean.Category;
import com.wangshuai.bean.SpecParams;
import com.wangshuai.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

@RequestMapping("list")
    public ResponseEntity<List<Category>> categoryTree(Long pid){
    List<Category> categories = categoryService.findCategoryByPid(pid);
    System.out.println(categories);
    return ResponseEntity.status(HttpStatus.OK).body(categories);
}

    @GetMapping("list/cids")
    public ResponseEntity<List<Category>> findById(@RequestParam("cids") List<Long> cids) {
        return ResponseEntity.ok(categoryService.findByCids(cids));
    }

    @GetMapping("params")
    public ResponseEntity<List<Category>> findCategoryByGid(@RequestParam(name = "gid",required = false) Long gid,
                                                            @RequestParam(name="cid",required = false)Long cid,
                                                            @RequestParam(name="searching",required = false)Boolean searching
    ) {
        return ResponseEntity.ok(categoryService.findByParams(gid, cid, searching));

    }


}
