package com.rin.rinpicturebackend.model.enums;

import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;

/**
 * @ ClassName PictureReviewStatusEnum
 * @ Description 图片审核状态枚举
 * @ Author Rin
 * @ Date 2025/10/13 14:07
 */
@Getter
public enum PictureReviewStatusEnum {
    UNREVIEWED("待审核", 0),
    APPROVED("通过", 1),
    REJECTED("拒绝", 2);

    private final String text;
    private final int value;

    PictureReviewStatusEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 根绝 value 获取审核枚举
     *
     * @param value 值
     */
    public static PictureReviewStatusEnum getEnumByValue(Integer value) {
        if (ObjectUtil.isEmpty(value)) {
            return null;
        }
        for (PictureReviewStatusEnum pictureReviewStatusEnum : PictureReviewStatusEnum.values()) {
            if (pictureReviewStatusEnum.value == value) {
                return pictureReviewStatusEnum;
            }
        }
        return null;
    }
}
