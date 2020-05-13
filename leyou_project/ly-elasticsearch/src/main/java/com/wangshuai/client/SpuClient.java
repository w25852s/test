package com.wangshuai.client;

import com.wangshuai.bean.api.SpuAPI;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("item-service")
public interface SpuClient  extends SpuAPI {
}
