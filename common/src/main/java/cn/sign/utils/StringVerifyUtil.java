package cn.sign.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringVerifyUtil {

    public static final String PHONE_PATTERN = "(^\\s{0}$)|(^(0\\d{2,3}\\-|0\\d{2,3})?[1-9]\\d{6,7}(\\-\\d{1,4})?$)|(^1(3|4|5|7|8|9)\\d{9}$)";
    public static final String MOBILE_PATTERN = "(^\\s{0}$)|(^1(3|4|5|7|8|9)\\d{9}$)";
    public static final String FAX_PATTERN = "^(^\\s{0}$)|(0\\d{2,3}\\-|0\\d{2,3})?[1-9]\\d{6,7}(\\-\\d{1,4})?$";
    public static final String NO_SPEC_CHAR_PATTERN = "[^`~!@#$%^&*=|{}:\\[\\].<>《》/?~！@#￥……&*——|{}【】；：。？]+";
    public static final String POST_CODE_PATTERN = "^[0-9]{6}$";
    public static final String NUMBER_PATTERN = "^\\d+$";

    public static Boolean isMobile(String str){
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile(MOBILE_PATTERN);
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    public static Boolean isFax(String str){
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile(FAX_PATTERN);
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    public static Boolean isEmail(String str){
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        String pattern = "^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$";
        p = Pattern.compile(pattern);
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    public static Boolean isPostCode(String str){
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile(POST_CODE_PATTERN);
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    public static Boolean isNumber(String str){
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile(NUMBER_PATTERN);
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    public static Boolean hasNoSpecialChar(String str){
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile(NO_SPEC_CHAR_PATTERN);
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    public static Boolean isEmpty(String source){
        if(source == null || "".equals(source.trim())){
            return true;
        }
        return false;
    }

    public static Boolean isNotEmpty(String source){
        return !isEmpty(source);
    }
}
