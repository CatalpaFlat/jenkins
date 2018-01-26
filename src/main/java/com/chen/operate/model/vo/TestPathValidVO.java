package com.chen.operate.model.vo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;

/**
 * @author ： CatalpaFlat
 * @date ：Create in 11:36 2018/1/16
 */
public class TestPathValidVO {
    @Getter
    @Setter
    @Min(value = 2,message = "isInt must be more than 2")
    private Integer isInt;
    @Getter
    @Setter
    @NotBlank(message = "isString is empty")
    private String isString;

}
