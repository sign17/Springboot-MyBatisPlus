package cn.sign.exception;

import cn.sign.model.ApiError;
import cn.sign.utils.R;
import lombok.extern.java.Log;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log
@RestControllerAdvice
public class ExceptionHandler {
    /**
     * 捕获异常
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(RuntimeException.class)
    public R handleException(Exception e){
        if(e instanceof AuthorizationException){
            e.printStackTrace();
            return new R(ApiError.INTERFACE_UNPREMITTED);
        }
        e.printStackTrace();
        return R.error(-1, e.getMessage());
    }

}
