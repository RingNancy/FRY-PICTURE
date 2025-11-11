package com.rin.rinpicturebackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rin.rinpicturebackend.model.dto.space.SpaceAddRequest;
import com.rin.rinpicturebackend.model.dto.space.SpaceQueryRequest;
import com.rin.rinpicturebackend.model.entity.Space;
import com.rin.rinpicturebackend.model.entity.User;
import com.rin.rinpicturebackend.model.vo.SpaceVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 18422
 * @description 针对表【space(空间)】的数据库操作Service
 * @createDate 2025-11-05 15:47:47
 */
public interface SpaceService extends IService<Space> {

    /**
     * 校验空间
     *
     * @param space
     * @param add
     */
    void validSpace(Space space, boolean add);

    /**
     * 根据空间级别自动填充限额
     *
     * @param space
     */
    void fillSpaceBySpaceLevel(Space space);


    /**
     * 获取空间
     *
     * @param space   图
     * @param request 请求
     */
    SpaceVO getSpaceVo(Space space, HttpServletRequest request);


    /**
     * 分页获取空间包装类
     *
     * @param spacePage 分页
     * @param request   请求
     */
    Page<SpaceVO> getSpaceVoPage(Page<Space> spacePage, HttpServletRequest request);


    /**
     * 查询条件
     *
     * @param spaceQueryRequest 查询请求
     * @return 查询结果
     */
    QueryWrapper<Space> getQueryWrapper(SpaceQueryRequest spaceQueryRequest);

    /**
     * 分页获取空间VO
     * @param spacePage
     * @param request
     * @return
     */
    Page<SpaceVO> getSpaceVOPage(Page<Space> spacePage, HttpServletRequest request);


    /**
     * 添加空间
     * @param spaceAddRequest
     * @param loginUser
     * @return
     */
    long addSpace(SpaceAddRequest spaceAddRequest, User loginUser);
}
