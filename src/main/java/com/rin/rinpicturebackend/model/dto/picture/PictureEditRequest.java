package com.rin.rinpicturebackend.model.dto.picture;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ ClassName PictureEditRequest 图片编辑请求，一般用户
 * @ Description
 * @ Author Rin
 * @ Date 2025/10/8 18:03
 */
@Data
public class PictureEditRequest implements Serializable {
    /**
     * 图片id
     */
    private Long id;


    private String name;

    private String introduction;

    private String category;

    private List<String> tags;

    public static final long serialVersionUID = 1L;
}
