package com.example.demo.util;

import java.util.HashMap;

/**
 * @program: demo
 * @description:
 * @author: py
 * @create: 2019-12-25 10:00
 **/
public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 362498829963181265L;

    public R() {
        put("code", "0");
        put("msg", "ok");
    }

    public static R ok() {
        return new R();
    }

    public static R ok(Object data) {
        R r = new R();
        r.put("data", data);
        return r;
    }

    public static R error(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("msg", msg);
        r.put("code", code);
        return r;
    }

    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
