package com.chen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
public class GradleDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(GradleDemoApplication.class, args);
    }

    @ResponseBody
    @GetMapping("/")
    public String hello() {
        return "Hello World!";
    }

}
