package cn.sign.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class DateUtil {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private static SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String timeStampToString(Timestamp timestamp) {
        String tsStr = "";
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            tsStr = sdf.format(timestamp);
            System.out.println(tsStr);
            return tsStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tsStr;
    }

    public static String timeStampToString(String format,Timestamp timestamp) {
        String tsStr = "";
        DateFormat sdf = new SimpleDateFormat(format);
        try {
            tsStr = sdf.format(timestamp);
            System.out.println(tsStr);
            return tsStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tsStr;
    }

    public static String dateToStrLong(java.util.Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 功能描述: 日期转换成字符串
     * @param dateDate 转换的日期
     * @param pattern 转换格式
     * @return java.lang.String
     */
    public static String dateToStrWithPattern(java.util.Date dateDate, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * description: 计算今天的剩余秒数
     * @param currentDate 当前日期
     * @return int
     */
    public static int endOfDate(java.util.Date currentDate) {
        LocalDateTime midnight = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault()).plusDays(1).withHour(0).withMinute(0)
                .withSecond(0).withNano(0);
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault());
        long seconds = ChronoUnit.SECONDS.between(currentDateTime, midnight);
        return (int) seconds;
    }

    public static Date parseDate(String str) {
        if (str == null) {
            return null;
        }
        try {
            java.util.Date utilDate = dateFormat.parse(str);
            return new Date(utilDate.getTime());
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parseDateWithException(String str) throws ParseException {
        if (str == null || str.length() == 0) {
            return null;
        }
        try {
            java.util.Date utilDate = dateFormat.parse(str);
            return new Date(utilDate.getTime());
        } catch (ParseException e) {
            throw e;
        }
    }

    public static Timestamp parseTimestamp(String str) {
        if (str == null) {
            return null;
        }
        try {
            java.util.Date utilDate = datetimeFormat.parse(str);
            return new Timestamp(utilDate.getTime());
        } catch (ParseException e) {
            try {
                java.util.Date utilDate = dateFormat.parse(str);
                return new Timestamp(utilDate.getTime());
            } catch (ParseException e1) {
                return null;
            }
        }
    }

    public static Timestamp parseTimestampWithException(String str) throws ParseException {
        if (str == null || str.length() == 0) {
            return null;
        }
        try {
            java.util.Date utilDate = datetimeFormat.parse(str);
            return new Timestamp(utilDate.getTime());
        } catch (ParseException e) {
            try {
                java.util.Date utilDate = dateFormat.parse(str);
                return new Timestamp(utilDate.getTime());
            } catch (ParseException e1) {
                throw e1;
            }
        }
    }

    /**
     * compareDate方法
     * <p>方法说明：
     * 比较endDate是否是晚于startDate；
     * 如果是，返回true， 否则返回false
     * </p>
     */
    public static boolean compareDate(String startDate, String endDate) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date1 = dateFormat.parse(startDate);
            java.util.Date date2 = dateFormat.parse(endDate);
            if (date1.getTime() > date2.getTime()) {
                return false;
            }
            //startDate时间上早于endDate
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * compareTime方法
     * <p>方法说明：
     * 比较endTime是否是晚于startTime；
     * 如果是，返回true， 否则返回false
     * </p>
     */
    public static boolean compareTime(String startTime, String endTime) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            java.util.Date date1 = dateFormat.parse(startTime);
            java.util.Date date2 = dateFormat.parse(endTime);
            if (date1.getTime() > date2.getTime()) {
                return false;
            }
            //startDate时间上早于endDate
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public static boolean compareTime(String startTime, String endTime, String pattern) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(pattern);
            java.util.Date date1 = dateFormat.parse(startTime);
            java.util.Date date2 = dateFormat.parse(endTime);
            if (date1.getTime() > date2.getTime()) {
                return false;
            }
            //startDate时间上早于endDate
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public static java.util.Date parseDateTime(String strDate) {
        try {
            java.util.Date date = datetimeFormat.parse(strDate);
            return date;
        } catch (ParseException px) {
            px.printStackTrace();
        }
        return null;
    }

    /**
     * 获取年份
     */
    public static List<String> getBetween(Integer minDate, Integer maxDate) {
        ArrayList<String> result = new ArrayList<String>();
        //格式化为年月
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        try {
            while (true) {
                result.add(minDate.toString());
                if (minDate < maxDate) {
                    minDate++;
                    continue;
                }
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getDiffDateConversionTime(java.util.Date endDate, java.util.Date beginDate) {
        long diff = Math.abs(endDate.getTime() - beginDate.getTime());
        Long hours = (diff) / (1000 * 60 * 60);
        Long minutes = (diff - hours * (1000 * 60 * 60)) / (1000 * 60);
        Long second = (diff - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / (1000);
        return numberZeroFill(hours.intValue()) + ":" + numberZeroFill(minutes.intValue()) + ":" + numberZeroFill(second.intValue());
    }

    public static String numberZeroFill(int v1) {
        int max = 10;
        if (v1 < max && v1 >= 0) {
            return "0" + v1;
        } else if (v1 > -max && v1 < 0) {
            return "-0" + Math.abs(v1);
        } else {
            return String.valueOf(v1);
        }
    }

    /**
     * 根据格式校验日期
     *
     * @param str
     * @param pattern
     * @return
     */
    public static boolean isValidDate(String str, String pattern) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            java.util.Date date = format.parse(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断middle时间是否在start时间与end时间之间
     *
     * @param start
     * @param end
     * @param middle
     * @return
     */
    public static Boolean isBetweenTime(String start, String end, String middle) {
        try {
            java.util.Date dateStart = timeFormat.parse(start);
            java.util.Date dateEnd = timeFormat.parse(end);
            java.util.Date dateMiddle = timeFormat.parse(middle);
            if (dateMiddle.getTime() >= dateStart.getTime() && dateMiddle.getTime() <= dateEnd.getTime()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
