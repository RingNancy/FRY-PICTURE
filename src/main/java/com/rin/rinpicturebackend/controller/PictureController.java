package com.rin.rinpicturebackend.controller;

import com.rin.rinpicturebackend.annotation.AuthCheck;
import com.rin.rinpicturebackend.common.BaseResponse;
import com.rin.rinpicturebackend.common.ResultUtils;
import com.rin.rinpicturebackend.constant.UserConstant;
import com.rin.rinpicturebackend.manager.CosManager;
import com.rin.rinpicturebackend.model.dto.picture.PictureUploadRequest;
import com.rin.rinpicturebackend.model.entity.User;
import com.rin.rinpicturebackend.model.vo.PictureVO;
import com.rin.rinpicturebackend.service.PictureService;
import com.rin.rinpicturebackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @ ClassName PictureController
 * @ Description
 * @ Author Rin
 * @ Date 2025/10/9 00:18
 */
@RestController
@RequestMapping("/picture")
@Slf4j
public class PictureController {

    @Resource
    private CosManager cosManager;

    @Resource
    private UserService userService;

    @Resource
    private PictureService pictureService;


    @PostMapping("/upload")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<PictureVO> uploadPicture(@RequestPart("file")MultipartFile multipartFile, PictureUploadRequest pictureUploadRequest,
                                                 HttpServletRequest request){
        User loginUser = userService.getLoginUser(request);
        PictureVO pictureVO = pictureService.uploadPicture(multipartFile, pictureUploadRequest, loginUser);
        return ResultUtils.success(pictureVO);
    }



}
