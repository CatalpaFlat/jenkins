package com.chen.controller;

import com.chen.core.config.CustomYmlConfig;
import com.chen.core.http.utils.ResponseUtil;
import com.chen.core.http.vo.Response;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * @author ： CatalpaFlat
 * @date ：Create in 22:29 2017/12/21
 */
@RestController
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class.getName());

    @Autowired
    private CustomYmlConfig customYmlConfig;

    @GetMapping("test")
    public Response getTest(){
        return ResponseUtil.success("test");
    }


    @GetMapping("promptLogin")
    public Response promptLogin(){
        return ResponseUtil.error(HttpStatus.UNAUTHORIZED.value(),"Need to authorization authentication");
    }
}
