package cn.sign.exception;

import cn.sign.model.ApiError;

public class ApiException extends RuntimeException {
    Integer errorCode;
    String msg;
    ApiError apiError;

    public ApiException(Integer errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
        this.msg = msg;
    }

    public ApiException(ApiError apiError) {
        super(apiError.msg);
        this.errorCode = apiError.errorCode;
        this.msg = apiError.msg;
        this.apiError = apiError;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public ApiError getApiError() {
        return apiError;
    }
}
