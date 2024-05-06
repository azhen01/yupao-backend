package com.zzlearning.yupao.model.domain.request;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户登录请求体
 */

@Data
public class TeamUpdateRequest implements Serializable {


    private static final long serialVersionUID = -4287266251289745626L;

    /**
     * id
     */
    private Long id;

    /**
     * 队伍名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;


    /**
     * 过期时间
     */
    private Date expireTime;


    /**
     * 0 - 公开，1 - 私有，2 - 加密
     */
    private Integer status;

    /**
     * 密码
     */
    private String password;


}
