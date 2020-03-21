package cn.sign.config;

import cn.sign.exception.ApiException;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Throwable exception = super.getError(webRequest);
        if (exception instanceof ApiException) {
            Map<String, Object> map = new HashMap<>(2);
            map.put("errorcode", ((ApiException) exception).getErrorCode());
            map.put("msg", ((ApiException) exception).getMsg());
            return map;
        } else {
            return super.getErrorAttributes(webRequest, includeStackTrace);
        }
    }
}
