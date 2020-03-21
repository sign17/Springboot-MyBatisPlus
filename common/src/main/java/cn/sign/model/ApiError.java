package cn.sign.model;

public enum ApiError {

    INTERFACE_UNPREMITTED(10006, "无访问权限"),

    // ================错误信息======================
    NUMBER_FORMAT_EXCEPTION(-1, "参数格式错误"),
    //空指针异常exception
    NULL_POINT_EXCEPTION(-1, "查无数据");


    public Integer errorCode;
    public String msg;

    ApiError(Integer errorCode, String msg) {
        this.errorCode = errorCode;
        this.msg = msg;
    }
}
