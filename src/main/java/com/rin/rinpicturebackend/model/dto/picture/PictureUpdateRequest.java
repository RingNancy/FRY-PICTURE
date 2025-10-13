package com.rin.rinpicturebackend.model.dto.picture;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ ClassName PictureUpdateRequest 图片更新请求，管理员权限
 * @ Description
 * @ Author Rin
 * @ Date 2025/10/8 18:03
 */
@Data
public class PictureUpdateRequest implements Serializable {
    /**
     * 图片id
     */
    private Long id;


    /**
     * 图片名称
     */
    private String name;

    /**
     * 图片简介
     */
    private String introduction;

    /**
     * 图片类别
     */
    private String category;

    /**
     * 标签
     */
    private List<String> tags;

    public static final long serialVersionUID = 1L;
}
