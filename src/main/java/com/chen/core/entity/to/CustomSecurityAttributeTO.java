package com.chen.core.entity.to;

import com.chen.core.entity.pojo.AntMatchersPOJO;
import com.chen.core.entity.pojo.OAuth2POJO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * security属性配置
 *
 * @author ： CatalpaFlat
 * @date ：Create in 21:11 2017/12/20
 */
public class CustomSecurityAttributeTO {

    @Getter
    @Setter
    private AntMatchersPOJO antMatchers;

    @Getter
    @Setter
    private OAuth2POJO oauth2s;
}
