package com.rin.rinpicturebackend.model.dto.user;

import com.rin.rinpicturebackend.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 用户查询请求
 *
 * @ ClassName UserAddRequest
 * @ Description
 * @ Author Rin
 * @ Date 2025/9/28 11:56
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserQueryRequest extends PageRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 昵称
     */
    private String UserName;

    /**
     * 简介
     */
    private String userProfile;


    /**
     * 账号
     */
    private String userAccount;

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
