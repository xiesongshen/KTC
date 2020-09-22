package com.xss.ktc.listener;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.xss.ktc.util.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author XSS
 * @date 2020/8/24
 * @desc
 */
@Component
@RabbitListener(queues = "sms")
public class SmsListener {

    @Autowired
    private SmsUtil smsUtil;

    @Value("${aliyun.sms.template_code}")
    private String template_code;

    @Value("${aliyun.sms.sign_name}")
    private String sign_name;


    @RabbitHandler
    public void sendSms(Map<String, String> dataMap) {

        String mobile = dataMap.get("mobile");
        String code = dataMap.get("code");
        System.out.println("手机号：" + mobile);
        System.out.println("验证码：" + code);
        String param = "{\"code\":\"" + code + "\"}";
        try {
            SendSmsResponse response = smsUtil.sendSms(mobile, template_code, sign_name, param);

            System.out.println(response.getCode());

        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
