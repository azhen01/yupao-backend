package com.zzlearning.yupao.model.domain.request;


import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {


    private static final long serialVersionUID = -4287266251289745626L;

    private String userAccount;

    private String userPassword;

}
