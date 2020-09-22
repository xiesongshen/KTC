package com.xss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

/**
 * @author XSS
 * @date 2020/8/18
 * @desc
 */
@SpringBootApplication
@EnableEurekaClient
public class BaseApplication {

    public static void main(String[] args) {

        SpringApplication.run(BaseApplication.class);

    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }
}
