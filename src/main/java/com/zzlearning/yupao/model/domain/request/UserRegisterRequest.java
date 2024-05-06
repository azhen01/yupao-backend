package com.zzlearning.yupao.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author 36447
 */
@Data
public class UserRegisterRequest implements Serializable {


    private static final long serialVersionUID = 5353385547771484619L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;

    private String planetCode;
}
