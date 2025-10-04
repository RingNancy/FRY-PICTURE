package com.rin.rinpicturebackend.service;

import cn.hutool.http.server.HttpServerRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rin.rinpicturebackend.model.dto.user.UserQueryRequest;
import com.rin.rinpicturebackend.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rin.rinpicturebackend.model.vo.LoginUserVO;
import com.rin.rinpicturebackend.model.vo.UserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 18422
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2025-09-27 17:25:26
*/
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param userAccount 账号
     * @param userPassword 密码
     * @param checkPassword 确认密码
     * @return 新用户ID
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);


    /**
     * 用户登录
     * @param userAccount 账号
     * @param userPassword 密码
     * @param request 请求
     * @return 脱敏之后的用户信息
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 加密密码，采用MD5
     * @param userPassword 密码
     * @return 返回加密之后的数据
     */
    String getEncryptPassword(String userPassword);


    /**
     * 获取当前登录用户
     * @param request 请求
     * @return 当前登录用户
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 获取已经脱敏的登录用户信息
     * @param user 用户
     * @return 脱敏的用户
     */
    LoginUserVO getLoginUserVO(User user);


    /**
     * 获取脱敏的用户信息
     * @param user 用户
     * @return
     */
    UserVO getUserVO(User user);


    /**
     * 获取脱敏用户信息列表
     * @param users 用户列表
     * @return
     */
    List<UserVO> getUserVOList(List<User> users);


    /**
     * 用户查询请求
     * @param userQueryRequest 请求
     * @return
     */
    QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);

    /**
     * 用户注销
     * @param request 请求
     * @return 注销布尔值
     */
    boolean userLogout(HttpServletRequest request);
}
