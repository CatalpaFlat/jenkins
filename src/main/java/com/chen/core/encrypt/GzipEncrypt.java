package com.chen.core.encrypt;

import sun.misc.BASE64Encoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author ： CatalpaFlat
 * @date ：Create in 13:18 2018/1/26
 */
public class GzipEncrypt {
    public static String zip(byte[] bytes) {
        if (bytes != null && bytes.length > 0) {
            ByteArrayOutputStream byteOs = new ByteArrayOutputStream();
            try (  GZIPOutputStream gZipOs = new GZIPOutputStream(byteOs)) {
                gZipOs.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    byteOs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 此处使用BASE64做转码。
            return new BASE64Encoder().encode(byteOs.toByteArray());
        }
        return null;
    }

    public static String unzip(byte[] bytes) {
        try (GZIPInputStream gZipIs = new GZIPInputStream(new ByteArrayInputStream(bytes));
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buff = new byte[512];
            int read = gZipIs.read(buff);
            while (read > 0) {
                bos.write(buff, 0, read);
                read = gZipIs.read(buff);
            }
            return new BASE64Encoder().encode(bos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
