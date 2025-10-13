package com.rin.rinpicturebackend.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.server.HttpServerRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rin.rinpicturebackend.exception.BusinessException;
import com.rin.rinpicturebackend.exception.ErrorCode;
import com.rin.rinpicturebackend.exception.ThrowUtils;
import com.rin.rinpicturebackend.model.dto.user.UserQueryRequest;
import com.rin.rinpicturebackend.model.entity.User;
import com.rin.rinpicturebackend.model.enums.UserRoleEnum;
import com.rin.rinpicturebackend.model.vo.LoginUserVO;
import com.rin.rinpicturebackend.model.vo.UserVO;
import com.rin.rinpicturebackend.service.UserService;
import com.rin.rinpicturebackend.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.rin.rinpicturebackend.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author 18422
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2025-09-27 17:25:26
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    @Override
    public boolean isAdmin(User user) {
        return user != null && user.getUserRole().equals(UserRoleEnum.ADMIN.getValue());
    }

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 校验
        if(StrUtil.hasBlank(userAccount, userPassword, checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号长度过短");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码长度不能低于8位");
        }
        if (!userPassword.equals(checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "输入的密码不一致");
        }
        // 检查重复
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userAccount", userAccount);
        long count = this.baseMapper.selectCount(userQueryWrapper);
        if (count > 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号已经存在");
        }

        // 加密
        String encryptPassword = getEncryptPassword(userPassword);

        // 插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setUserName("CrazyGuys");
        user.setUserRole(UserRoleEnum.USER.getValue());

        boolean saveResult = this.save(user);
        if (!saveResult){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
        }
        
        return user.getId();
    }

    @Override
    public LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 校验
        if (StrUtil.hasBlank(userAccount, userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号密码不能为空");
        }
        if (userAccount.length() < 4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号错误");
        }
        if (userPassword.length() < 8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误");
        }

        // 加密
        String encryptPassword = getEncryptPassword(userPassword);
        // 检查用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = this.baseMapper.selectOne(queryWrapper);
        if (user == null){
            log.info("user login failed, userAccount can't match userPassword");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号或者密码错误");
        }
        // 通过Session保存当前用户的登录状态
        request.getSession().setAttribute(USER_LOGIN_STATE, user);
        return this.getLoginUserVO(user);
    }

    @Override
    public boolean userLogout(HttpServletRequest request) {
        //首先判断是否登录
        Object objUser = request.getSession().getAttribute(USER_LOGIN_STATE);
        if (objUser == null){
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "未登录");
        }
        // 清楚登录信息
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return true;
    }



    @Override
    public User getLoginUser(HttpServletRequest request) {
        //首先判断是否登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        // 从数据库查询;
        Long userId = currentUser.getId();
        currentUser = this.getById(userId);
        if (currentUser == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        return currentUser;
    }

    @Override
    public LoginUserVO getLoginUserVO(User user) {
        if (user == null){
            return null;
        }
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(user, loginUserVO);
        return loginUserVO;
    }

    @Override
    public UserVO getUserVO(User user) {
        if (user == null){
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    public List<UserVO> getUserVOList(List<User> users) {
        if (CollUtil.isEmpty(users)){
            return new ArrayList<>();
        }
        return users.stream().map(this::getUserVO).collect(Collectors.toList());
    }

    @Override
    public QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest) {
        if (userQueryRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long id = userQueryRequest.getId();
        String userProfile = userQueryRequest.getUserProfile();
        String userAccount = userQueryRequest.getUserAccount();
        String userName = userQueryRequest.getUserName();
        String userRole = userQueryRequest.getUserRole();
        String sortField = userQueryRequest.getSortField();
        String sortOrder = userQueryRequest.getSortOrder();

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq(ObjectUtil.isNotNull(id), "id", id);
        userQueryWrapper.eq(StrUtil.isNotBlank(userRole), "userRole", userRole);
        userQueryWrapper.like(StrUtil.isNotBlank(userAccount), "userAccount", userAccount);
        userQueryWrapper.like(StrUtil.isNotBlank(userName), "userName", userName);
        userQueryWrapper.like(StrUtil.isNotBlank(userProfile), "userProfile", userProfile);
        userQueryWrapper.orderBy(StrUtil.isNotEmpty(sortField), sortOrder.equals("ascend"), sortField);

        return userQueryWrapper;
    }

    @Override
    public String getEncryptPassword(String userPassword) {
        final String SALT = "rin";
        return DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
    }
}




