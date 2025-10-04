package com.rin.rinpicturebackend.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @ ClassName DeleteRequest
 * @ Description 通用的删除请求类
 * @ Author Rin
 * @ Date 2025/9/26 19:30
 */
@Data
public class DeleteRequest implements Serializable {
    private Long id;

    private static final long serialVersionUID = 1L;

}


