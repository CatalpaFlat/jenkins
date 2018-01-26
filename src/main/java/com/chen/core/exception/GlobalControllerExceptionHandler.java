package com.chen.core.exception;

import com.chen.core.model.vo.ResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 全局的异常处理
 *
 * @author Administrator
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public ResponseEntity<ResponseVO> exception(Exception ex) {
        logger.info("ex:" + ex.getMessage());
        return new ResponseEntity<>(new
                ResponseVO(ex.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

    // valid exception

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ResponseVO> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder errorMesssage = new StringBuilder("Invalid Request:");

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMesssage.append(fieldError.getDefaultMessage()).append(", ");
        }

        logger.error(bindingResult.getFieldError().getDefaultMessage());
        return new ResponseEntity<>(new
                ResponseVO(errorMesssage.toString()), HttpStatus.BAD_REQUEST);
    }

    // missing Parameter
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ResponseEntity<ResponseVO> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException ex) {
        logger.error(ex.getMessage(),ex);
        return new ResponseEntity<>(new
                ResponseVO(HttpStatus.BAD_REQUEST.value(),ex.getMessage(),null), HttpStatus.BAD_REQUEST);
    }

}
