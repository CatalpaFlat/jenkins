package com.chen.operate.idal;

import com.chen.core.annotation.BodyParamValid;
import com.chen.core.annotation.ParameterValid;
import com.chen.core.annotation.PathAndQueryParamValid;
import com.chen.core.encrypt.AESEncrypt;
import com.chen.core.model.vo.ResponseVO;
import com.chen.operate.model.vo.TestPathValidVO;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author ： CatalpaFlat
 * @date ：Create in 10:43 2018/1/17
 */
@RestController
public class TestController {
    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @PathAndQueryParamValid
    @GetMapping("path/{isInt}/{isString}")
    public ResponseVO pathGet(@PathVariable @ParameterValid(type = Integer.class, msg = "isInt must be more than 2", isMin = true, min = 2) Integer isInt,
                              @PathVariable @ParameterValid(type = String.class, msg = "isString is empty") String isString) {
        log.info("int:" + isInt);
        log.info("String:" + isString);
        JSONObject resultJson = new JSONObject();
        resultJson.put("isInt", isInt);
        resultJson.put("isString", isString);
        return new ResponseVO(HttpStatus.OK.value(), "pathGet", resultJson);
    }

    @GetMapping("query")
    @PathAndQueryParamValid
    public ResponseVO queryGet(@RequestParam @ParameterValid(type = Integer.class, msg = "isInt must be more than 2 ", isMin = true, min = 2) Integer isInt,
                               @RequestParam @ParameterValid(type = String.class, msg = "isString is empty") String isString) {
        log.info("int:" + isInt);
        log.info("String:" + isString);
        JSONObject resultJson = new JSONObject();
        resultJson.put("isInt", isInt);
        resultJson.put("isString", isString);
        return new ResponseVO(HttpStatus.OK.value(), "queryGet", resultJson);
    }


    @ResponseBody
    @BodyParamValid
    @PostMapping("body")
    public ResponseVO bodyPost(@RequestBody @Valid TestPathValidVO body) {
        log.info("int:" + body.getIsInt());
        log.info("String:" + body.getIsString());
        JSONObject resultJson = new JSONObject();
        resultJson.put("isInt", body.getIsInt());
        resultJson.put("isString", body.getIsString());
        return new ResponseVO("bodyPost", resultJson);
    }

    @PostMapping("/encrypt")
    public byte[] testUploadFile(HttpServletRequest req) throws Exception {
        ServletInputStream inputStream = req.getInputStream();
        //unzip decrypt
        String unzip = decompressForGzip(inputStream);
        //aes decrypt
        String aesDecrypt = AESEncrypt.getInstance().decrypt(unzip);
        //base64 decrypt
        byte[] base64Decrypt = new BASE64Decoder().decodeBuffer(aesDecrypt);

        String decryptStr = new String(base64Decrypt);
        log.info("decryptStr:"+decryptStr);


        //base64 encode
        String base64Encode = new BASE64Encoder().encode(base64Decrypt);
        //aes encode
        String aesEncode= AESEncrypt.getInstance().encrypt(base64Encode);
        //zip encode
        return compressForGzip(aesEncode);
    }
    /**
     * Gzip 压缩数据
     */
    public static byte[] compressForGzip(String unGzipStr) {

        if (StringUtils.isBlank(unGzipStr)) {
            return null;
        }
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(baos);
            gzip.write(unGzipStr.getBytes());
            gzip.close();
            byte[] bytes = baos.toByteArray();
            baos.flush();
            baos.close();
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Gzip解压数据
     */
    public static String decompressForGzip(ServletInputStream inputStream) {
        final int BUFFERSIZE = 100000;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            GZIPInputStream gzip = new GZIPInputStream(inputStream);
            byte[] buffer = new byte[BUFFERSIZE];
            int n = 0;
            while ((n = gzip.read(buffer, 0, buffer.length)) > 0) {
                out.write(buffer, 0, n);
            }
            gzip.close();
            out.close();
            return out.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
