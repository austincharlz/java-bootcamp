package com.northstar.crm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.northstar.crm.config.NorthstarIntegrationProperties;

@SpringBootApplication
@EnableConfigurationProperties(NorthstarIntegrationProperties.class)
public class CrmApplication {
    public static void main(String[] args) {
        SpringApplication.run(CrmApplication.class, args);
    }
}