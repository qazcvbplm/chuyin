package com.wxutil;

import java.math.BigDecimal;


/** @description 金额元分之间转换工具�?
 * @author @
 */
public class AmountUtils {


    /** 金额为分的格�? */
    public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";
    /**
     * 将分为单位的转换为元 （除100�?
     * 
     * @param amount
     * @return
     * @throws Exception
     */
    public static String changeF2Y(String amount) throws Exception {
        if (!amount.matches(CURRENCY_FEN_REGEX)) {
            throw new Exception("金额格式有误");
        }
        return BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100)).toString();
    }




    /**
     * 将元为单位的转换为分 替换小数点，支持以�?�号区分的金�?
     * 
     * @param amount
     * @return
     */
    public static String changeY2F(String amount) {
        String currency = amount.replaceAll("\\$|\\￥|\\,", ""); // 处理包含, �?
                                                                // 或�??$的金�?
        int index = currency.indexOf(".");
        int length = currency.length();
        Long amLong = 0L;
        if (index == -1) {
            amLong = Long.valueOf(currency + "00");
        } else if (length - index >= 3) {
            amLong = Long.valueOf((currency.substring(0, index + 3)).replace(".", ""));
        } else if (length - index == 2) {
            amLong = Long.valueOf((currency.substring(0, index + 2)).replace(".", "") + 0);
        } else {
            amLong = Long.valueOf((currency.substring(0, index + 1)).replace(".", "") + "00");
        }
        return amLong.toString();
    }
} 
