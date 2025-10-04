package com.rin.rinpicturebackend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @ ClassName UserRegisterRequest
 * @ Description
 * @ Author Rin
 * @ Date 2025/9/27 19:06
 */
@Data
public class UserRegisterRequest implements Serializable {
    public static final long serialVersionUID = 3191241716373120793L;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 确认密码
     */
    private String checkPassword;
}
