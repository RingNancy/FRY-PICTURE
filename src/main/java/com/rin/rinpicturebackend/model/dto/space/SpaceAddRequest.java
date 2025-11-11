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
public class SpaceAddRequest implements Serializable {
    /**
     * 空间名称
     */
    private String spaceName;

    /**
     * 空间级别：0-普通版本 1-专业版 2-旗舰版
     */
    private Integer spaceLevel;

    private static final long serialVersionUID = 1L;
}
