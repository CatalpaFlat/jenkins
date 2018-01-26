package com.chen.core.support;

import com.chen.core.model.vo.ResponseVO;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author ： CatalpaFlat
 * @date ：Create in 11:54 2018/1/17
 */
public class AdvanceResponseSupport {
    private static final Logger log = LoggerFactory.getLogger(AdvanceResponseSupport.class);

    public static void advanceResponse(ResponseVO body) {
        ServletRequestAttributes res = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = res.getResponse();
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpStatus.BAD_REQUEST.value());

        OutputStream output = null;
        try {
            output = response.getOutputStream();
            body.setCode(null);
            String error = new Gson().toJson(body);
            log.info("aop 检测到参数不规范" + error);
            output.write(error.getBytes("UTF-8"));
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

}
