package com.yyl.client.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 时间转换工具
 *
 * @author 严亚龙
 */
public class DUtil {

    public static final String TMP_1 = "yyyy-MM-dd HH:mm:ss";
    public static final String TMP_2 = "yyyy-MM-dd";

    private static final String CST = "EEEMMMddHH:mm:ss'CST'yyyy";

    private static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";

    private static final String[] formatArr = new String[]{"yyyyMMddHHmmss", "yyyyMddHHmmss", "yyyyMMdHHmmss", "yyyyMdHHmmss"};


    /**
     * 字符串转Date
     */
    public static Date str2date(String str) {
        str = str.replaceAll("\\s{1,}", "");//去掉一个或多个空格
        SimpleDateFormat sdf = null;
        Date date = null;
        try {
            if (str.contains("CST")) {
                sdf = new SimpleDateFormat(CST, Locale.US);
                date = sdf.parse(str);
            } else if (str.contains(".0Z")) {
                sdf = new SimpleDateFormat(yyyyMMddHHmmss);
                date = sdf.parse(str);
            } else {
                date = s2d(str);
            }

            return date;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 字符串转Date(转换格式)
     */
    public static Date str2date(String str, String format) {
        if (format.isEmpty()) {
            return str2date(str);
        } else if (str.length() < format.length()) {
            return str2date(str);
        } else {
            SimpleDateFormat sd = new SimpleDateFormat(format);
            try {
                return sd.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
    }


    /**
     * Date格式化
     */
    public static String date2str(Date date, String format) {
        SimpleDateFormat date2str = new SimpleDateFormat(format);
        return date2str.format(date);
    }

    /**
     * Date格式化
     */
    public static String date2str(Object obj, String format) {
        SimpleDateFormat date2str = new SimpleDateFormat(format);
        return date2str.format(obj);
    }

    /**
     * 字符串转Date 再 格式化
     */
    public static String ostr2nstr(String oldstr, String newstr) {
        return date2str(str2date(oldstr), newstr);
    }

    /**
     * 获得当前的格式化时间
     *
     * @return 格式化时间
     */
    public static String getCurrentStr() {
        return date2str(System.currentTimeMillis(), TMP_1);
    }

    public static String utc2Local(String utcTime) {
        SimpleDateFormat utcFormater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gpsUTCDate = null;
        try {
            gpsUTCDate = utcFormater.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat localFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        localFormater.setTimeZone(TimeZone.getDefault());
        String localTime = localFormater.format(gpsUTCDate.getTime());
        return localTime;
    }


    public static Calendar getCurrentUtcTime() {
        //1、获取当前时间
        Calendar cal = Calendar.getInstance();
        //System.out.println(cal.getTime());
        //2、取得时间偏移量：
        final int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        //System.out.println(zoneOffset);
        //3、取得夏令时差：
        final int dstOffset = cal.get(Calendar.DST_OFFSET);
        //System.out.println(dstOffset);
        //4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));

        return cal;
    }

    public static String getStartTime() {
        Calendar cal;
        cal = getCurrentUtcTime();

        //cal.add(Calendar.MILLISECOND, -(20 * 60 * 1000));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return sdf.format(cal.getTime());
    }

    /**
     * 时间比较
     * 例子 compare("2016-10-11 15:01:13", "=", "2016-10-11 15:01:13")
     *
     * @param str1 待比较的 时间字符
     * @param str2 待比较的 时间字符
     * @return true or false
     */
    public static boolean compare(String str1, String opt, String str2) {
        return compare(s2d(str1), opt, s2d(str2));
    }

    public static boolean compare(String str1, String opt, Date d2) {
        return compare(s2d(str1), opt, d2);
    }

    public static boolean compare(Date d1, String opt, Date d2) {
        opt = opt.replaceAll("\\s{1,}", "");//去掉一个或多个空格
        int n = compare(d1.getTime(), d2.getTime());

        if (">".equals(opt)) {
            return 1 == n;
        } else if (">=".equals(opt)) {
            return 1 == n || 0 == n;
        } else if ("=".equals(opt)) {
            return 0 == n;
        } else if ("<=".equals(opt)) {
            return -1 == n || 0 == n;
        } else if ("<".equals(opt)) {
            return -1 == n;
        }


        return false;
    }

    /**
     * 时间毫秒值比较大小
     *
     * @param t1
     * @param t2
     * @return 1 大于, 0 等于, -1 小于
     */
    public static int compare(long t1, long t2) {
        if (t1 > t2) {
            return 1;
        } else if (t1 < t2) {
            return -1;
        } else {
            return 0;
        }
    }


    // 获取当前日期的天毫秒值
    public static Date dayStartDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    private static Date s2d(String str) {
        // 提取数字
        str = getNum(str);
        // 智能匹配
        return zn(str);
    }

    // 提取数字
    private static String getNum(String str) {
        StringBuffer num = new StringBuffer();

        Pattern p = Pattern.compile("[0-9]");
        Matcher m = p.matcher(str);
        while (m.find()) {
            num.append(m.group());
        }

        return num.toString();
    }


    // 智能循环
    private static Date zn(String str) {

        Date date = null;

        try {

            SimpleDateFormat sd;
            String format;
            String nStr;

            for (int i = 0; i < formatArr.length; i++) {
                format = formatArr[i];
                format = format.substring(0, str.length());
                sd = new SimpleDateFormat(format);
                date = sd.parse(str);

                // 在格式化回字符串
                nStr = date2str(date, format);
                // str 和 nstr 相等则转换成功
                if (str.endsWith(nStr)) {
                    break;
                }
            }

        } catch (ParseException e) {
            System.out.println("格式不对，请指定格式");
            e.printStackTrace();
        }

        return date;
    }


}
