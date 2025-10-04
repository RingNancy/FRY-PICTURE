package com.rin.rinpicturebackend.exception;

import lombok.Getter;

/**
 * @ ClassName BusinessException
 * @ Description
 * @ Author Rin
 * @ Date 2025/9/26 16:51
 */
@Getter
public class BusinessException extends RuntimeException {
    /**
     * 错误码
     */
    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

}
