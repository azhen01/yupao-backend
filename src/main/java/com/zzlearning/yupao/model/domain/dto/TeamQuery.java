package com.zzlearning.yupao.model.domain.dto;

import com.zzlearning.yupao.common.PageRequest;
import lombok.Data;

import java.util.List;


/**
 * 队伍查询封装类
 */
@Data
public class TeamQuery extends PageRequest {
    /**
     * id
     */
    private Long id;

    /**
     * 队伍名称
     */
    private String name;

    /**
     * 搜索关键词(同时对队伍名称和描述搜索)
     */
    private String searchText;

    /**
     * 描述
     */
    private String description;

    /**
     * 最大人数
     */
    private Integer maxNum;


    /**
     * 用户id
     */
    private Long userId;

    /**
     * 0 - 公开，1 - 私有，2 - 加密
     */
    private Integer status;

    /**
     * 所有id
     */
    private List<Long> idList;


}
