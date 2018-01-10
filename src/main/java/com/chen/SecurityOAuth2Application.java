package com.chen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * start
 *
 * @author ： CatalpaFlat
 * @date ：Create in 22:06 2017/12/21
 */
@SpringBootApplication
@PropertySource("classpath:properties/custom.properties")
public class SecurityOAuth2Application {
    private static final Logger logger = LoggerFactory.getLogger(SecurityOAuth2Application.class);

    public static void main(String[] args) {
        SpringApplication.run(SecurityOAuth2Application.class, args);
        logger.info("SecurityOAuth2Application start success...");
    }
}
