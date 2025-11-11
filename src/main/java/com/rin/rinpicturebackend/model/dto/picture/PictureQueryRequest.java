package com.rin.rinpicturebackend.model.dto.picture;

import com.rin.rinpicturebackend.common.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @ ClassName PictureQueryRequest 图片查询请求，继承分页查询请求PageRequest
 * @ Description
 * @ Author Rin
 * @ Date 2025/10/8 18:03
 */
@Data
public class PictureQueryRequest extends PageRequest implements Serializable {
    /**
     * 图片id
     */
    private Long id;

    private String name;

    private String introduction;

    private String category;

    private List<String> tags;

    private Long picSize;

    private Integer picWidth;

    private Integer picHeight;

    /**
     * 图片比例
     */
    private Double picScale;

    /**
     * 图片格式
     */
    private String picFormat;

    /**
     * 搜索关键词(同时搜索名称、标签等)
     */
    private String searchText;

    private Long spaceId;

    /**
     * 是否只查询SpaceId为Null的
     */
    private boolean nullSpaceId;

    /**
     * 用户id
     */
    private Long userId;

    private Integer reviewStatus;

    private String reviewMessage;

    private Long reviewerId;

    private Date reviewTime;

    public static final long serialVersionUID = 1L;
}
