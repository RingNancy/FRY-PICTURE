package com.rin.rinpicturebackend.common;

import com.rin.rinpicturebackend.exception.ErrorCode;
import lombok.Data;

import java.io.Serializable;

/**
 * @ ClassName BaseResponse
 * @ Description 全局响应封装类
 * @ Author Rin
 * @ Date 2025/9/26 17:09
 */
@Data
public class BaseResponse<T> implements Serializable {

    private int code;

    private T data;

    private String message;

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BaseResponse(int code, T data) {
        this(code, data, "");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());
    }


}
