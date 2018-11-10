package meifeng.mobile.kevin.com.meifeng.dao;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

/**
 * Created by kevin.w on 2018/4/3.
 */
public class Encrypt {

    public Encrypt(){}

    /* BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * BASE64加密
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        BASE64Encoder encoder = new BASE64Encoder();

        return encoder.encodeBuffer(key);
    }


}
