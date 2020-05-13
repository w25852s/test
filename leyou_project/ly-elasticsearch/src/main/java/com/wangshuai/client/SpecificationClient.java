package com.wangshuai.client;

import com.wangshuai.bean.api.SpecificationAPI;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("item-service")
public interface SpecificationClient extends SpecificationAPI {
}
