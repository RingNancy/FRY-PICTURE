package com.rin.rinpicturebackend.model.dto.file;

import lombok.Data;

/**
 * @ ClassName UploadPictureResult 用于接收图片上解析信息的包装类
 * @ Description
 * @ Author Rin
 * @ Date 2025/10/8 18:11
 */
@Data
public class UploadPictureResult {

    /**
     * 图片地址
     */
    private String url;

    /**
     * 图片名称
     */
    private String picName;

    /**
     * 文件体积
     */
    private Long picSize;

    /**
     * 图片宽度
     */
    private int picWidth;

    /**
     * 图片高度
     */
    private int picHeight;

    /**
     * 图片宽高比
     */
    private Double picScale;

    /**
     * 图片格式
     */
    private String picFormat;

}

