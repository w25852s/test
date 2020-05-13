package com.wangshuai.client;

import com.wangshuai.bean.api.SkuAPI;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("item-service")
public interface SkuClient  extends SkuAPI {
}
