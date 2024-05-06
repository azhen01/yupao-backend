package com.zzlearning.yupao.model.domain.request;


import lombok.Data;

import java.io.Serializable;

/**
 * 用户退出队伍请求体
 */

@Data
public class TeamQuitRequest implements Serializable {


    private static final long serialVersionUID = -4287266251289745626L;

    /**
     * id
     */
    private Long teamId;

}
