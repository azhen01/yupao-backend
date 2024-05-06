package com.zzlearning.yupao.common;


import lombok.Data;

import java.io.Serializable;

/**
 * 通用分页请求参数
 * @author 36447
 *
 */
@Data
public class PageRequest implements Serializable {


    private static final long serialVersionUID = -8731304084642888815L;
    /**
     * 页面大小
     */
    protected int pageSize = 10;

    /**
     * 当前是第几页
     */
    protected int pageNum = 1;


}
