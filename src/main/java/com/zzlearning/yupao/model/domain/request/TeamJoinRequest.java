package com.zzlearning.yupao.model.domain.request;


import lombok.Data;

import java.io.Serializable;

/**
 * 用户加入队伍请求体
 */

@Data
public class TeamJoinRequest implements Serializable {


    private static final long serialVersionUID = -4287266251289745626L;

    /**
     * id
     */
    private Long teamId;

    /**
     * 密码
     */
    private String password;


}
