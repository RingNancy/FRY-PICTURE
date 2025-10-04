package com.rin.rinpicturebackend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @ ClassName UserLoginRequest
 * @ Description
 * @ Author Rin
 * @ Date 2025/9/28 00:18
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;


}
