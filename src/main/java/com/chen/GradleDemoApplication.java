package com.chen;

import com.chen.core.constant.SfConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * start
 *
 * @author ： CatalpaFlat
 * @date ：Create in 9:56 2018/1/5
 */
@Controller
@SpringBootApplication
@PropertySource("classpath:properties/system.properties")
public class GradleDemoApplication {
    private static final Logger log = LoggerFactory.getLogger(GradleDemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(GradleDemoApplication.class, args);
        log.info("GradleDemoApplication success....");
        String sfSameDomain = SfConstant.SF_SAME_DOMAIN;
        log.info("env:" + sfSameDomain);
    }

    @ResponseBody
    @GetMapping("/")
    public String hello() {
        return "Hello World!";
    }


}
