package meifeng.mobile.kevin.com.meifeng.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NumberUtil {

    public static String getNormalNumber(Double number) {
        NumberFormat nf = new DecimalFormat("#0.00");
        String str = nf.format(number);
        return str;
    }

    public static String getNumberString(String value, int size) {
        float num = Float.parseFloat(value);
        BigDecimal b = new BigDecimal(num);
        //保留5位并且属于四舍五入类型，当然这里的四舍五入没有任何意义，可以选择其他类型。
        return b.setScale(size, BigDecimal.ROUND_HALF_UP).toString();
    }

    public static String getNormalNumber(String number) {
        String res = "0.00";
        if (number != null) {
            res = String.format("%.2f", Float.parseFloat(number)); // 12345.68
        }
        return res;
    }

    public static String getNormalNumber(String number, int size) {
        String res = "0.00";
        if (number != null) {
            res = String.format("%." + size + "f", Float.parseFloat(number)); // 12345.68
        }
        return res;
    }
}
