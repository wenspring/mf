package meifeng.mobile.kevin.com.meifeng.dao;

/**
 * Created by kevin.w on 2018/4/3.
 */
public class BASE64Encrypt {


    /**
     * 数据加密方法
     * @param code 需要加密的内容
     * @return 加密后的数据
     */
    public static String encryptCode(String code){
        try {
            Encrypt objEncrypt = new Encrypt();
            byte[] codeByte = code.getBytes();
            return objEncrypt.encryptBASE64(codeByte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 数据解密方法
     * @param code 填入该算法加密过的内容
     * @return 解密后的内容
     */
    public static String deciphering(String code){
        try {
            Encrypt objEncrypt = new Encrypt();
            byte[] deciphering = objEncrypt.decryptBASE64(code);
            return new String(deciphering);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
