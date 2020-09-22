package com.xss.ktc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author XSS
 * @date 2020/8/26
 * @desc
 */
@SpringBootApplication
@EnableEurekaServer   //开启Eureka服务器端
public class EurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class);
    }
}
