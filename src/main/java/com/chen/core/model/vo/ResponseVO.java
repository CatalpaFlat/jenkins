package com.chen.core.model.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author ： CatalpaFlat
 * @date ：Create in 11:55 2018/1/16
 */
public class ResponseVO implements Serializable {
    @Getter
    @Setter
    private Integer code;
    @Getter
    @Setter
    private String msg;
    @Getter
    @Setter
    private Object obj;

    public ResponseVO(int value, String msg, Object obj) {
        this.code = value;
        this.msg = msg;
        this.obj = obj;
    }

    public ResponseVO(String message, Object o) {
        this.msg = message;
        this.obj = o;
        this.code = null;
    }

    public ResponseVO(String msg) {
        this.msg = msg;
    }
}
