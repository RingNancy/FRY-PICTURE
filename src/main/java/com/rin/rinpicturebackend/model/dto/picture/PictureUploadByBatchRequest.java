package com.rin.rinpicturebackend.model.dto.picture;

import lombok.Data;

/**
 * @ ClassName PictureUploadByBatchRequest
 * @ Description
 * @ Author Rin
 * @ Date 2025/10/29 00:00
 */
@Data
public class PictureUploadByBatchRequest {

    /**
     * 搜索关键字
     */
    private String searchText;

    /**
     * 每页数量
     */
    private Integer count = 10;
}
