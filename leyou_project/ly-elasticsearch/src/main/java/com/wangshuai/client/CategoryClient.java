package com.wangshuai.client;

import com.wangshuai.api.BrandAPI;
import com.wangshuai.bean.Category;
import com.wangshuai.bean.api.CategoryAPI;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("item-service")
public interface CategoryClient extends CategoryAPI {



}
