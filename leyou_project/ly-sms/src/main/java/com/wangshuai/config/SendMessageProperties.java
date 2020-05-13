package com.wangshuai.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "ly.sm")
public class SendMessageProperties {
    private String key;
    private String keyScret;
    private String smsCode;
    private String signame;

}
