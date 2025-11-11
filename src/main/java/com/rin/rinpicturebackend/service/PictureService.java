package com.rin.rinpicturebackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rin.rinpicturebackend.model.dto.picture.PictureQueryRequest;
import com.rin.rinpicturebackend.model.dto.picture.PictureReviewRequest;
import com.rin.rinpicturebackend.model.dto.picture.PictureUploadByBatchRequest;
import com.rin.rinpicturebackend.model.dto.picture.PictureUploadRequest;
import com.rin.rinpicturebackend.model.entity.Picture;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rin.rinpicturebackend.model.entity.User;
import com.rin.rinpicturebackend.model.vo.PictureVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
* @author 18422
* @description 针对表【picture(图片)】的数据库操作Service
* @createDate 2025-10-08 17:52:25
*/
public interface PictureService extends IService<Picture> {

    /**
     * 上传图片
     *
     * @param inputSource        文件
     * @param pictureUploadRequest 参数
     * @param loginUser            登录用户
     * @return 图片包装类
     */
    PictureVO uploadPicture (Object inputSource, PictureUploadRequest pictureUploadRequest, User loginUser);


    /**
     * 批量上传图片
     *
     * @param pictureUploadByBatchRequest 批量上传图片请求
     * @param loginUser                   登录用户
     * @return 批量上传图片数量
     */
    Integer uploadPictureByBatch(PictureUploadByBatchRequest pictureUploadByBatchRequest, User loginUser);

    /**
     * 获取单张图片
     *
     * @param picture 图
     * @param request 请求
     */
    PictureVO getPictureVO(Picture picture, HttpServletRequest request);


    /**
     * 分页获取图片包装类
     *
     * @param picturePage 分页
     * @param request     请求
     */
    Page<PictureVO> getPictureVOPage(Page<Picture> picturePage, HttpServletRequest request);


    /**
     * 验证图片
     *
     * @param picture
     */
    void validPicture(Picture picture);


    /**
     * 图片审核
     *
     * @param pictureReviewRequest
     * @param loginUser
     */
    void doPictureReview(PictureReviewRequest pictureReviewRequest, User loginUser);

    /**
     * 清理图片文件
     * @param oldPicture
     */
    void clearPictureFile(Picture oldPicture);

    /**
     * 文件审核参数
     *
     * @param picture
     * @param loginUser
     */
    void fileReviewParams(Picture picture, User loginUser);

    /**
     *  检查图片的权限，防止意外删除，新增空间之后，如果图片属于私人空间，仅允许自己删除
     * @param loginUser
     * @param picture
     */
    void checkPictureAuth(User loginUser, Picture picture);


    /**
     * 删除图片
     * @param pictureId
     * @param loginUser
     */
    void deletePicture(long pictureId, User loginUser);

    /**
     * 查询条件
     *
     * @param pictureQueryRequest 查询请求
     * @return 查询结果
     */
    QueryWrapper<Picture> getQueryWrapper(PictureQueryRequest pictureQueryRequest);
}
