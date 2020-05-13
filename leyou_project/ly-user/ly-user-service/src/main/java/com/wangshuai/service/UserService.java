package com.wangshuai.service;

import com.wangshuai.bean.User;
import com.wangshuai.dao.UserDao;
import com.wangshuai.enum1.ExceptionEnum;
import com.wangshuai.exception.LyException;
import com.wangshuai.utils.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    private static final String KEY_PREFIX = "code.phone.";

    @Autowired
    private UserDao userDao;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private StringRedisTemplate  redisTemplate;


    public Boolean checkData(String data, Integer type) {

        User user = new User();
        switch (type) {
            case 0:
                user.setUsername(data);
                break;

            case 1:
                user.setPhone(data);
                break;
             default:
                    throw new LyException(ExceptionEnum.INVALID_TYPE);
        }

         int count = userDao.selectCount(user);
       return count==0;

    }

    public void codeVerify(String phone) {
        String key=KEY_PREFIX+phone;
        String code = NumberUtils.generateCode(6);
        Map<String, String> msg = new HashMap<>();
        msg.put("phone", phone);
        msg.put("code", code);
        rabbitTemplate.convertAndSend("ly.sms.exchange", "sms.verify.code", msg);

        redisTemplate.opsForValue().set(key,code,5, TimeUnit.MINUTES);

    }
}
