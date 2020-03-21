package cn.sign.utils;

import cn.sign.model.ApiError;

import java.util.HashMap;

public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	public R() {
		put("code", 0);
		put("msg", "操作成功");
	}

	public R(int code, String msg) {
		put("code", code);
		put("msg", msg);
	}

	public static R error() {
		return error(1, "操作失败");
	}

	public static R error(String msg) {
		return error(500, msg);
	}

	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static R ok(Object object) {
		R r = new R();
		r.put("data",object);
		return r;
	}

	public static R ok() {
		return new R();
	}

	@Override
	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}

	public R(ApiError apiError) {
		super.put("errorcode", apiError.errorCode);
		super.put("msg", apiError.msg);
	}
}
