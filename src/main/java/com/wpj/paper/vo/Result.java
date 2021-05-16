package com.wpj.paper.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Result<T> {

    private String code;

    private String msg;

    private T data;

    public static <T> Result<T> ok() {
        return Result.ok(null);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>("200", "success", data);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<>("-1", msg, null);
    }
}
