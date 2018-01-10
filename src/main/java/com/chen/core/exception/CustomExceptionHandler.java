package com.chen.core.exception;

import com.chen.core.http.utils.ResponseUtil;
import com.chen.core.http.vo.Response;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常
 *
 * @author ： CatalpaFlat
 * @date ：Create in 9:40 2017/12/21
 */
@ControllerAdvice
public class CustomExceptionHandler {
    @ResponseBody
    @ExceptionHandler(CustomException.class)
    public Response handlerNotExistException(CustomException e, HttpServletResponse response) {
        response.setStatus(e.getCode());
        return ResponseUtil.error(e.getCode(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    public Response handlerNotExistException(AccessDeniedException e, HttpServletResponse response) {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        return ResponseUtil.error(HttpStatus.FORBIDDEN.value(), e.getMessage());
    }
    @ResponseBody
    @ExceptionHandler(AuthenticationException.class)
    public Response handlerNotExistException(AuthenticationException e, HttpServletResponse response) {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        return ResponseUtil.error(HttpStatus.FORBIDDEN.value(), e.getMessage());
    }
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Response handlerNotExistException(Exception e, HttpServletResponse response) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}
