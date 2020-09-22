package com.xss.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author XSS
 * @date 2020/8/27
 * @desc
 */
@SpringBootApplication
@EnableConfigServer   //开启SpringCloudConfig集中配置服务器
public class ConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class);
    }
}
