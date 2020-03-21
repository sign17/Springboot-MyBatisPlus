package cn.sign.utils;

import cn.sign.exception.ApiException;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleVerifyUtil {

    private static final Integer ERRORCODE = -1;
    private static final String JPG = "jpg";
    private static final String JPEG = "jpeg";
    private static final String PNG = "png";
    private static final String GIF = "gif";

    /**
     * 功能描述: 验证是否为空
     * @param object 待验证Object
     * @param fieldName 待验证Object名称
     * @return void
     */
    public static void notNull(Object object, String fieldName){
        if(object == null) {
            throw new ApiException(ERRORCODE,fieldName+"不能为空");
        }
    }

    /**
     * 功能描述: 验证字符串
     * @param str 待验证字符串
     * @param fieldName 待验证字符串名称
     * @param notNull 是否验证空
     * @param maxLength 最大长度，为空则不验证
     * @return void
     */
    public static void verifyString(String str, String fieldName, boolean notNull, Integer maxLength) {
        if(str == null || "".equals(str)) {
            if(notNull) {
                throw new ApiException(ERRORCODE,fieldName+"不能为空");
            }
        } else {
            if(maxLength != null) {
                if(str.length() > maxLength) {
                    throw new ApiException(ERRORCODE,fieldName+"最大长度为"+maxLength);
                }
            }
        }
    }

    /**
     * 功能描述: 验证日期
     * @param str 待验证字符串
     * @param fieldName 待验证字符串名称
     * @param notNull 是否验证空
     * @param pattern 验证日期格式
     * @return Date
     */
    public static Date verifyDateString(String str, String fieldName, boolean notNull, String pattern) {

        if(str == null || "".equals(str)) {
            if(notNull) {
                throw new ApiException(ERRORCODE,fieldName+"不能为空");
            }
            return null;
        } else {
            try {
                SimpleDateFormat format = new SimpleDateFormat(pattern);
                return format.parse(str);
            } catch (Exception e) {
                throw new ApiException(ERRORCODE,fieldName+"格式错误");
            }
        }
    }

    /**
     * description: 文件是否是图片
     * @param file
     * @return java.lang.Boolean
     */
    public static Boolean isPicture(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if(fileName != null && !"".equals(fileName)) {
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            if(JPG.equals(suffix) || JPEG.equals(suffix) || PNG.equals(suffix) || GIF.equals(suffix)) {
                return true;
            }
        }
        return false;
    }
}
