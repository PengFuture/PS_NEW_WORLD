package com.china.world;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author Peng
 * @date 2022/3/1 11:47
 */
@SpringBootApplication
@EnableDiscoveryClient
@RefreshScope
public class WorldManageApplication {
    public static void main(String[] args) {
        SpringApplication.run(WorldManageApplication.class, args);
    }
}
