package com.rin.rinpicturebackend.model.dto.picture;

import lombok.Data;

import java.io.Serializable;

/**
 * @ ClassName PictureReviewRequest
 * @ Description
 * @ Author Rin
 * @ Date 2025/10/13 14:13
 */
@Data
public class PictureReviewRequest implements Serializable {
    /**
     * 图片id
     */
    private Long Id;

    private Integer reviewStatus;

    private String reviewMessage;

    private static final long serialVersionUID = 1L;
}
