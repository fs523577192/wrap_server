package org.firas.wrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@ComponentScan({"org.firas"})
@EntityScan({
        "org.firas.wrap.entity", "org.firas.jiadian.entity",
        "org.firas.weixin.model", "org.firas.sms.model"})
@EnableJpaRepositories({
        "org.firas.wrap.repository", "org.firas.jiadian.repository",
        "org.firas.weixin.dao", "org.firas.sms.dao"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
