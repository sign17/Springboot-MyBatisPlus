package cn.sign.exception;

import cn.sign.model.ApiError;
import cn.sign.utils.R;
import lombok.extern.java.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Log
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public R handleApiException(ApiException e) {
        return R.error(e.errorCode, e.msg);
    }

    /**
     * 捕获异常
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public R handleException(Exception e){
        if(e instanceof NumberFormatException){
            e.printStackTrace();
            return new R(ApiError.NUMBER_FORMAT_EXCEPTION);
        }
        if(e instanceof NullPointerException){
            e.printStackTrace();
            return new R(ApiError.NULL_POINT_EXCEPTION);
        }
        if(e instanceof MethodArgumentTypeMismatchException){
            e.printStackTrace();
            return new R(ApiError.NUMBER_FORMAT_EXCEPTION);
        }
        if(e instanceof DataAccessException){
            e.printStackTrace();
            return new R(ApiError.NULL_POINT_EXCEPTION);
        }
        e.printStackTrace();
        return R.error(-1, e.getMessage());
    }

}
