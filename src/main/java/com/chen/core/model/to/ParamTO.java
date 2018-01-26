package com.chen.core.model.to;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ： CatalpaFlat
 * @date ：Create in 9:57 2018/1/18
 */
public class ParamTO {
    @Getter
    @Setter
    private List<ParamValidTO> valid;
    @Setter
    private List<String> types;

    public List<String> getTypes() {
        types = new ArrayList<>();
        for (ParamValidTO paramValidTO : valid) {
            types.add(paramValidTO.getClassType());
        }
        return types;
    }
}
