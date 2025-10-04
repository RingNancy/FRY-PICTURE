package com.rin.rinpicturebackend.common;

import lombok.Data;

/**
 * @ ClassName PageRequest
 * @ Description 通用分页请求类
 * @ Author Rin
 * @ Date 2025/9/26 19:27
 */
@Data
public class PageRequest {

    /**
     * 当前页号
     */
    private int current = 1;

    /**
     * 分页大小
     */
    private int pageSize = 10;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序方式（默认升序）
     */
    private String sortOrder = "descend";
}
