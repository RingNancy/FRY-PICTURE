package com.rin.rinpicturebackend.model.dto.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ ClassName UserEditRequest
 * @ Description
 * @ Author Rin
 * @ Date 2025/10/5 19:35
 */
@Data
public class UserEditRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 昵称
     */
    private String userName;

    /**
     * 头像
     */
    private String userAvatar;

    /**
     * 用户简介
     */
    private String userProfile;

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
    private Date birthday;


    private static final long serialVersionUID = 1L;
}
