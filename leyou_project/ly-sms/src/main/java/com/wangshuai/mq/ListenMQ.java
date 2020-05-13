package com.wangshuai.mq;

import com.wangshuai.config.SendMessageProperties;
import com.wangshuai.sms.SendMessage;
import com.wangshuai.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

@Slf4j
@Component
@EnableConfigurationProperties(SendMessageProperties.class)
public class ListenMQ {

    @Autowired
    private SendMessage sendMessage;

    @Autowired
    private SendMessageProperties prop;

    @RabbitListener(bindings = @QueueBinding(value = @Queue(name = "sms.verify.code.queue"),
            exchange = @Exchange(name = "ly.sms.exchange", durable = "true", type = ExchangeTypes.TOPIC), key = "sms.verify.code"))
    public void listenCode(Map<String, String> msg) {
        if (CollectionUtils.isEmpty(msg)) {
            return;
        }
        String phone = msg.remove("phone");
        if (StringUtils.isBlank(phone)) {
            return;
        }
        sendMessage.sendMessage(phone, prop.getSigname(),prop.getSmsCode(), JsonUtils.serialize(msg));
        log.info(" 【短信发送成功 手机号为:】  "+phone);
    }

}
