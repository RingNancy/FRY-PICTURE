package com.rin.rinpicturebackend.model.dto.space;

import lombok.Data;

import java.io.Serializable;

/**
 * @ ClassName SpaceAddRequest
 * @ Description
 * @ Author Rin
 * @ Date 2025/11/5 15:53
 */
@Data
public class SpaceUpdateRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 空间名称
     */
    private String spaceName;

    /**
     * 空间级别：0-普通版本 1-专业版 2-旗舰版
     */
    private Integer spaceLevel;

    /**
     * 空间图片的最大总大小
     */
    private Long maxSize;

    /**
     * 空间图片的最大数量
     */
    private Long maxCount;

    private static final long serialVersionUID = 1L;
}
