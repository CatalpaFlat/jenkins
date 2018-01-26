package com.chen.core.encrypt;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author ： CatalpaFlat
 * @date ：Create in 11:07 2018/1/26
 */
public class AESEncrypt {
    /**
     * 加密用的Key 可以用26个字母和数字组成
     * 此处使用AES-128-CBC加密模式，key需要为16位。
     */
    private String sKey = "dankal1234567891";
    private String ivParameter = "01234567abcdefgl";
    public static final String KEY_ALGORITHM = "AES";
    public static final String ECB_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    public static final String CBC_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

    private static AESEncrypt instance = null;

    private AESEncrypt() {

    }

    public static AESEncrypt getInstance() {
        if (instance == null) {
            instance = new AESEncrypt();
        }
        return instance;
    }
    /**
     * 加密
     */
    public String encrypt(String sSrc) throws Exception {
        Cipher cipher = Cipher.getInstance(CBC_CIPHER_ALGORITHM);
        byte[] raw = sKey.getBytes();
        SecretKeySpec secretKey  = new SecretKeySpec(raw, KEY_ALGORITHM);
        // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, secretKey , iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        // 此处使用BASE64做转码。
        return new String(encrypted);
    }

    /**
     * 解密
     */
    public  String decrypt(String sSrc) throws Exception {
        try {
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec secretKey = new SecretKeySpec(raw, KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CBC_CIPHER_ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            // 先用base64解密
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, "utf-8");
        } catch (Exception ex) {
            return null;
        }
    }

}
