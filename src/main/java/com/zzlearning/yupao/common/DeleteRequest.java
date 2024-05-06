package com.zzlearning.yupao.common;


import lombok.Data;

import java.io.Serializable;

/**
 * 通用删除请求
 * @author 36447
 *
 */
@Data
public class DeleteRequest implements Serializable {


    private static final long serialVersionUID = -8731304084642888815L;


    private long id;
}
