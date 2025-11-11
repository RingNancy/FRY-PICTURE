package com.rin.rinpicturebackend.model.dto.space;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ ClassName SpaceLevel
 * @ Description
 * @ Author Rin
 * @ Date 2025/11/12 00:54
 */
@Data
@AllArgsConstructor
public class SpaceLevel {

    /**
     * 值
     */
    private int value;

    /**
     *  中文
     */
    private String text;

    private long maxCount;

    private long maxSize;
}
