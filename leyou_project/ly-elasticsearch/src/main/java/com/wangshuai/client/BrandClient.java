package com.wangshuai.client;

import com.wangshuai.api.BrandAPI;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("brand-service")
public interface BrandClient extends BrandAPI {
}
