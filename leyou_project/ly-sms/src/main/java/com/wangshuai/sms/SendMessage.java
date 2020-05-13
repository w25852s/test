package com.wangshuai.sms;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import com.wangshuai.config.SendMessageProperties;
import com.wangshuai.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@EnableConfigurationProperties(SendMessageProperties.class)
@Component
public class SendMessage {
    private static final String PREFIX_KEY = "sms.phone.";

    private static final long MIN_INTERNAL_TIME=60000;

    @Autowired
    private SendMessageProperties prop;

    @Autowired
    private StringRedisTemplate  redisTemplate;



    // 设置鉴权参数，初始化客户端
   /* private DefaultProfile profile = DefaultProfile.getProfile(
            "cn-qingdao",// 地域ID
            prop.getKey(),// 您的AccessKey ID
            prop.getKeyScret());// 您的AccessKey Secret
    private IAcsClient client = new DefaultAcsClient(profile);*/

    public void sendMessage(String phoneNumbers,String sigName,String templateCode,String TemplateParam) {
        String key=PREFIX_KEY+phoneNumbers;
        //限流设置
        String lastTime = redisTemplate.opsForValue().get(key);
        if (StringUtils.isNotBlank(lastTime)) {
            if (System.currentTimeMillis() - Long.valueOf(lastTime) < MIN_INTERNAL_TIME) {
                log.info("【手机号：】"+phoneNumbers+"    发送频繁 ！ 间隔 需要大于 "+(MIN_INTERNAL_TIME/1000/60)+"   分钟 ");
                return ;
            }
        }
        DefaultProfile profile = DefaultProfile.getProfile(
                "cn-qingdao",// 地域ID
                prop.getKey(),// 您的AccessKey ID
                prop.getKeyScret());// 您的AccessKey Se

        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        // 接收短信的手机号码
        request.putQueryParameter("PhoneNumbers", phoneNumbers);
        // 短信签名名称。请在控制台签名管理页面签名名称一列查看（必须是已添加、并通过审核的短信签名）。
        request.putQueryParameter("SignName", sigName);
        // 短信模板ID
        request.putQueryParameter("TemplateCode", templateCode);
        // 短信模板变量对应的实际值，JSON格式。
        request.putQueryParameter("TemplateParam", JsonUtils.serialize(TemplateParam));
        CommonResponse commonResponse = null;
        try {
            commonResponse = client.getCommonResponse(request);

        } catch (ClientException e) {
            log.info(e.toString());
        }
        String data = commonResponse.getData();
        String sData = data.replaceAll("'\'", "");
        log.info( sData);
        Gson gson = new Gson();
        Map map = gson.fromJson(sData, Map.class);
        Object bizId = map.get("BizId");
        Object ok = map.get("Code");
        if(!"ok".equals(ok.toString())){
            log.info(map.get("Message").toString());
            return;
        }
        redisTemplate.opsForValue().set(key, String.valueOf(System.currentTimeMillis()), 20, TimeUnit.MINUTES);

    }
}
