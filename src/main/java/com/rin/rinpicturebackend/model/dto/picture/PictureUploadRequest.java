package com.rin.rinpicturebackend.model.dto.picture;

import lombok.Data;

import java.io.Serializable;

/**
 * @ ClassName PictureUploadRequest 图片也许需要重复上传，但是参数不变，需要修改id
 * @ Description
 * @ Author Rin
 * @ Date 2025/10/8 18:03
 */
@Data
public class PictureUploadRequest implements Serializable {
    /**
     * 图片id
     */
    private Long id;

    /**
     * 空间id
     */
    private Long spaceId;

    /**
     * 文件url地址
     */
    private String fileUrl;

    private static final long serialVersionUID = 1L;
}
