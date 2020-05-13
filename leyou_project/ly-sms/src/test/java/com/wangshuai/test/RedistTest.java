package com.wangshuai.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RedistTest {

    private static final String PREFIX_KEY = "sms.phone.";

    private static final long MIN_INTERNAL_TIME=60000;
    @Autowired

    private StringRedisTemplate template;
@Test
    public void test1() {
        String phoneNumbers = "15092260187";
        String key=PREFIX_KEY+phoneNumbers;
        String lastTime = template.opsForValue().get(key);
        if (StringUtils.isNotBlank(lastTime)&&System.currentTimeMillis()-Long.valueOf(lastTime)<MIN_INTERNAL_TIME) {
            log.info("【手机号】" + phoneNumbers + "  频繁发送");
            return;
        }

        template.opsForValue().set(key,String.valueOf(System.currentTimeMillis()),1, TimeUnit.MINUTES);
    }
}
