package com.rin.rinpicturebackend.common;

import com.rin.rinpicturebackend.exception.ErrorCode;

/**
 * @ ClassName ResultUtils
 * @ Description 响应结果工具类
 * @ Author Rin
 * @ Date 2025/9/26 19:00
 */
public class ResultUtils {

    /**
     * 成功响应
     *
     * @param data 数据
     * @param <T>  类型
     * @return 响应
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, "ok");
    }

    /**
     * 失败
     *
     * @param errorCode 错误码
     * @return 相应
     */
    public static BaseResponse<?> error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    /**
     * 失败
     *
     * @param errorCode 错误代码
     * @param message   错误信息
     * @return 响应
     */
    public static BaseResponse<?> error(ErrorCode errorCode, String message) {
        return new BaseResponse<>(errorCode.getCode(), null, message);
    }

    /**
     * 错误
     *
     * @param code    自定义错误码
     * @param message 错误信息
     * @return 响应
     */
    public static BaseResponse<?> error(int code, String message) {
        return new BaseResponse<>(code, null, message);
    }
}
