package com.github.tifezh.kchart.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * Description
 * Author puyantao
 * Email 1067899750@qq.com
 * Date 2019-1-14 17:18
 */

public class StrUntils {

    public static String deletePerCent(String str){
        if (str.contains("%")) {
            return str.substring(0, str.indexOf("%"));
        } else {
            return str;
        }
    }
    /**
     * 格式化空判断
     * @param str
     * @return
     */
    public static float strToFloat(String str){
        if (str == null || str.equals("-")|| str.equals("- -")) {
            return 0;
        }
        try {
            return Float.parseFloat(str);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("数据异常");
        }
    }

    /**
     *  匹配正负号
     * @param str
     * @return
     */
    public static boolean matchAddSubMark(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     *  判断末尾是小数点
     * @param str
     * @return
     */
    public static boolean matchFinishPoint(String str) {

        Pattern pattern = Pattern.compile("^[+\\-]+([1-9][0-9]*)+(.[0-9]{1,})?\\.$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     *  去掉小数点后多余的0
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s){
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }


    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }


    /**
     * @Title: fmtMicrometer
     * @Description: 格式化数字为千分位
     * @param text
     * @return    设定文件
     * @return String    返回类型
     */
    public static String fmtMicrometer(String text) {
        DecimalFormat df = null;
        if (text.indexOf(".") > 0) {
            if (text.length() - text.indexOf(".") - 1 == 0) {
                df = new DecimalFormat("###,##0.");
            } else if (text.length() - text.indexOf(".") - 1 == 1) {
                df = new DecimalFormat("###,##0.0");
            } else {
                df = new DecimalFormat("###,##0.00");
            }
        } else {
            df = new DecimalFormat("###,##0");
        }
        double number = 0.0;
        try {
            number = Double.parseDouble(text);
        } catch (Exception e) {
            number = 0.0;
        }
        return df.format(number);
    }

    public static void main(String[] argc){
        System.out.println(fmtMicrometer("45579690.12354"));
    }

}










