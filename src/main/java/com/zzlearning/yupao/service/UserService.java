package com.zzlearning.yupao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzlearning.yupao.model.domain.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 36447
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2023-09-06 20:07:29
*/
public interface UserService extends IService<User> {



    /**
     *
     * @param userAccount 用户
     * @param userPassword 用户密码
     * @param checkPassword 校验密码
     * @param planetCode 星球编号
     * @return id
     */
    long userRegister(String userAccount,String userPassword,String checkPassword,String planetCode);

    /**
     *
     * @param userAccount 用户
     * @param userPassword 用户密码
     * @param request 校验密码
     * @return 脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword,HttpServletRequest request);


    /**
     *确保用户信息是否安全
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);

    /**
     *注销用户
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);

    /**
     * 根据标签搜索用户
     *
     * @param tagNameList
     * @return
     */
    List<User> searchUsersByTags(List<String> tagNameList);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    int updateUser(User user,User loginUser);



    /**
     * 获取当前登录用户信息
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    /**
     *
     * @param request
     * @return
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     *
     * @param loginUser
     * @return
     */
    boolean isAdmin(User loginUser);

    /**
     * 匹配用户
     * @param num
     * @param loginUser
     * @return
     */
    List<User> matchUsers(long num, User loginUser);
}
