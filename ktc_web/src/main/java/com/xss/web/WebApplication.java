package com.xss.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author XSS
 * @date 2020/8/27
 * @desc
 */
@SpringBootApplication
@EnableEurekaClient    //eureka客户端
@EnableZuulProxy       //开启网管代理
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class);
    }

}
