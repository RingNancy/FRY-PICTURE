package com.rin.rinpicturebackend.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ ClassName PictureCategory
 * @ Description
 * @ Author Rin
 * @ Date 2025/10/9 18:12
 */
@Data
public class PictureTagCategory implements Serializable {
    private List<String> tagList;
    private List<String> categoryList;
}
