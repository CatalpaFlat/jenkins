package com.chen.core.security.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 封装的AuthenticationBean类
 *
 * @author ： CatalpaFlat
 * @date ：Create in 10:07 2017/12/25
 */
@Getter
@Setter
public class ParseTokenPOJO {
    private boolean isPass ;
    private Object msg;

    public ParseTokenPOJO(boolean isPass, Object msg) {
        this.isPass = isPass;
        this.msg = msg;
    }
}
