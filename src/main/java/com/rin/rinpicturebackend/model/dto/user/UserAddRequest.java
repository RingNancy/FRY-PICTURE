package com.rin.rinpicturebackend.model.dto.user;

import javassist.SerialVersionUID;
import lombok.Data;

import java.io.Serializable;

/**
 * @ ClassName UserAddRequest
 * @ Description
 * @ Author Rin
 * @ Date 2025/9/28 11:56
 */
@Data
public class UserAddRequest implements Serializable {

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 昵称
     */
    private String UserName;

    /**
     * 头像
     */
    private String userAvatar;

    /**
     * 用户简介
     */
    private String UserProfile;

    /**
     * 用户角色 user, admin
     */
    private String UserRole;

    /**
     * 性别（0-女，1-男，2-保密）
     */
    private Integer gender;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 生日
     */
    private String birthday;


    private static final long serialVersionUID = 1L;
}
