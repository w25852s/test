package com.wangshuai.bean.api;

import com.wangshuai.bean.SpecParams;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface SpecificationAPI {
    @GetMapping("spec/params")
    public List<SpecParams> findParamsByGid(@RequestParam(name = "gid", required = false) Long gid,
                                            @RequestParam(name = "cid", required = false) Long cid,
                                            @RequestParam(name = "searching", required = false) Boolean searching
    );
}
