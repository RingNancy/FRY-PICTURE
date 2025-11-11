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
public class SpaceEditRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 空间名称
     */
    private String spaceName;

    private static final long serialVersionUID = 1L;
}
