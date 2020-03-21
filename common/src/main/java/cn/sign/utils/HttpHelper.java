package cn.sign.utils;


import com.baomidou.mybatisplus.core.toolkit.StringPool;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

public final class HttpHelper {

    /**
     * 获取HTTP请求参数
     *
     * @param request
     * @return
     */
    public static Map<String, String> getRequestParams(ServletRequest request) {
        Map<String, String> params = new HashMap<>(request.getParameterMap().size());
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String key = names.nextElement();
            params.put(key, request.getParameter(key));
        }
        return params;
    }

    /**
     * 接收方通用签名
     *
     * @param params
     * @param signName
     * @param keyName
     * @param keyValue
     * @return
     */
    public static String signByReceiver(Map<String, String> params, String signName, String keyName,
            String keyValue) {
        params.remove(signName);
        StringBuilder signBuilder = new StringBuilder();
        params.keySet().stream().sorted().forEach(element -> {
            signBuilder.append(element).append("=").append(params.get(element)).append("&");
        });
        signBuilder.append(keyName).append("=").append(keyValue);
        return CryptUtils.md5(signBuilder.toString());
    }

    /**
     * 发送方通用签名
     *
     * @param parmas
     * @param keyName
     * @param keyValue
     * @return
     */
    public static String signBySender(Map<String, String> parmas, String keyName, String keyValue) {
        StringBuilder signBuilder = new StringBuilder();
        parmas.keySet().stream().sorted().forEach(element -> {
            signBuilder.append(element).append("=").append(parmas.get(element)).append("&");
        });
        signBuilder.append(keyName).append("=").append(keyValue);
        return CryptUtils.md5(signBuilder.toString());
    }

    /**
     * 获取ip地址
     * @param request
     * @return
     */
    public static String getIpAddress(ServletRequest request) {
        if (request instanceof HttpServletRequest) {
            String ip = ((HttpServletRequest) request).getHeader("x-forwarded-for");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = ((HttpServletRequest) request).getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = ((HttpServletRequest) request).getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            return ip;
        }
        return request.getRemoteAddr();
    }

    /**
     * 获取请求参数
     * @param queryString 请求字符串
     * @return 请求参数
     */
    public static Map<String, String> parseQuery(String queryString) {
        if (queryString == null) {
            return Collections.emptyMap();
        } else {
            Map<String, String> parameterMap = new TreeMap<>();
            String[] queryArr = queryString.split(StringPool.AMPERSAND);
            for (String string : queryArr) {
                String[] keyAndValue = string.split(StringPool.EQUALS, 2);
                if (keyAndValue.length != 2) {
                    parameterMap.put(keyAndValue[0], StringPool.EMPTY);
                } else {
                    parameterMap.put(keyAndValue[0], keyAndValue[1]);
                }
            }
            return parameterMap;
        }
    }

}
