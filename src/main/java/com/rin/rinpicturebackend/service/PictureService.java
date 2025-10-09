package com.rin.rinpicturebackend.service;

import com.rin.rinpicturebackend.model.dto.picture.PictureUploadRequest;
import com.rin.rinpicturebackend.model.entity.Picture;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rin.rinpicturebackend.model.entity.User;
import com.rin.rinpicturebackend.model.vo.PictureVO;
import org.springframework.web.multipart.MultipartFile;

/**
* @author 18422
* @description 针对表【picture(图片)】的数据库操作Service
* @createDate 2025-10-08 17:52:25
*/
public interface PictureService extends IService<Picture> {
    PictureVO uploadPicture (MultipartFile multipartFile, PictureUploadRequest pictureUploadRequest, User loginUser);
}
