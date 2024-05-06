package com.zzlearning.yupao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zzlearning.yupao.common.ErrorCode;
import com.zzlearning.yupao.constant.UserConstant;
import com.zzlearning.yupao.mapper.UserMapper;
import com.zzlearning.yupao.model.domain.User;
import com.zzlearning.yupao.model.domain.expection.BusinessException;
import com.zzlearning.yupao.service.UserService;
import com.zzlearning.yupao.utils.AlgorithmUtils;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.zzlearning.yupao.constant.UserConstant.ADMIN_ROLE;
import static com.zzlearning.yupao.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author 36447
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2023-09-06 20:07:29
*/

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private UserMapper userMapper;

    private static final String SALT = "zhangzhen";



    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword,String planetCode) {
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword,planetCode)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
//            return -1;
        }
        if(userAccount.length() < 4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账号太短");
//            return -1;
        }
        if(userPassword.length() < 8 || checkPassword.length() < 8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码太短");
//            return -1;
        }

        if(planetCode.length() > 5 ){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"星球编号过长");
//            return -1;
        }

        String vaildPatten="[[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\\n|\\r|\\t]+";
        Matcher matcher = Pattern.compile(vaildPatten).matcher(userAccount);
        if(matcher.find()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"存在未知字符");
        }

        if(!userPassword.equals(checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码校验不一致");
        }
        //账户不能重复
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>();
        userQueryWrapper.eq("userAccount",userAccount);
        long count = userMapper.selectCount(userQueryWrapper);
        if(count > 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号不能重复");
        }
        //星球编号不能重复
        userQueryWrapper = new QueryWrapper<User>();
        userQueryWrapper.eq("planetCode",planetCode);
        count = userMapper.selectCount(userQueryWrapper);
        if(count > 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"星球编号不能重复");
        }
        //加盐
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + "password").getBytes());
        //插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setPlanetCode(planetCode);
        boolean saveResult = this.save(user);
        if(!saveResult){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"插入数据错误");
        }

        return user.getId();
    }



    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if(userAccount.length() < 4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号过短");
        }
        if(userPassword.length() < 8 ){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码过长");
        }

        String vaildPatten="[[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\\n|\\r|\\t]+";
        Matcher matcher = Pattern.compile(vaildPatten).matcher(userAccount);
        if(matcher.find()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号存在未知字符");
        }

        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + "password").getBytes());

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>();
        userQueryWrapper.eq("userAccount",userAccount);
        userQueryWrapper.eq("userPassword",encryptPassword);
        User user = userMapper.selectOne(userQueryWrapper);
        if(user == null){
            log.info("user login failed, userAccount cannot match userPassword.");
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"登录失败");
        }
//        记录用户登录态

        User safetyUser = getSafetyUser(user);

        request.getSession().setAttribute(USER_LOGIN_STATE,safetyUser);


        return safetyUser;



    }

    @Override
    public User getSafetyUser(User originUser) {
        if (originUser == null){
            return  null;
        }
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setPlanetCode(originUser.getPlanetCode());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setCreateTime(originUser.getCreateTime());
        safetyUser.setTags(originUser.getTags());
        return safetyUser;
    }

    /**
     * 注销
     * @param request
     * @return
     */
    @Override
    public int userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }

    /**
     * 根据标签查询用户
     *
     * @param tagNameList 用户要有的标签
     * @return
     */
    @Override
    public List<User> searchUsersByTags(List<String> tagNameList){
        if(CollectionUtils.isEmpty(tagNameList)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        for (String tagName : tagNameList) {
//            queryWrapper = queryWrapper.like("tags",tagName);
//        }
//        List<User> userList = userMapper.selectList(queryWrapper);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        List<User> userList = userMapper.selectList(queryWrapper);
        Gson gson = new Gson();
        return userList.stream().filter(user -> {
            String tagsStr = user.getTags();
            if(StringUtils.isBlank(tagsStr)){
                return false;
            }
            Set<String> tempTagNameList = gson.fromJson(tagsStr, new TypeToken<Set<String>>(){}.getType());
            for(String tagName : tagNameList){
                if(!tempTagNameList.contains(tagName)){
                    return false;
                }
            }
            return true;
        }).map(this::getSafetyUser).collect(Collectors.toList());
//        return userList.stream().map(this::getSafetyUser).collect(Collectors.toList());
    }

    @Override
    public int updateUser(User user,User loginUser) {
        long userId = user.getId();
        if(userId <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //如果是管理员，允许更新任何用户
        //如果不是管理员，只允许更新当前(自己的)信息
        if(!isAdmin(loginUser) || userId != loginUser.getId()){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        //仅管理员和自己可修改
        User oldUser = userMapper.selectById(userId);
        if (oldUser == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return userMapper.updateById(user);
    }

    /**
     * 是否为管理员
     * @param request
     * @return
     */
    @Override
    public boolean isAdmin(HttpServletRequest request) {
            Object userObj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
            User user = (User) userObj;
            return user != null && user.getUserRole() == ADMIN_ROLE;
    }

    /**
     * 是否为管理员
     * @param loginUser
     * @return
     */
    @Override
    public boolean isAdmin(User loginUser) {
        return loginUser != null && loginUser.getUserRole() == ADMIN_ROLE;
    }

    @Override
    public List<User> matchUsers(long num, User loginUser) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","tags");
        queryWrapper.isNotNull("tags");
        List<User> userList = this.list(queryWrapper);
        String tags = loginUser.getTags();
        Gson gson = new Gson();
        List<String> tagList = gson.fromJson(tags, new TypeToken<List<String>>() {
        }.getType());
        List<Pair<User,Long>> list = new ArrayList<>();
//        SortedMap<Integer,Long> indexDistanceMap = new TreeMap<>();
        // 依次计算所以用户和当前用户的相似度
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            String userTags = user.getTags();
            // 无标签或者当前用户自己
            if (StringUtils.isBlank(userTags) || user.getId() == loginUser.getId()){
                continue;
            }
            List<String> userTagList = gson.fromJson(userTags, new TypeToken<List<String>>() {
            }.getType());
            // 计算分数
            long distance = AlgorithmUtils.minDistance(tagList, userTagList);
//            indexDistanceMap.put(i,distance);
            list.add(new Pair<>(user,distance));
        }
        // 按编辑距离由小到大排序
        List<Pair<User,Long>> topUserPairList = list.stream()
                .sorted((a,b) -> (int) (a.getValue() - b.getValue()))
                .limit(num)
                .collect(Collectors.toList());
        // 原本顺序的 userId 列表
        List<Long> userIdList = topUserPairList.stream().map(pair -> pair.getKey().getId()).collect(Collectors.toList());
        QueryWrapper<User>  userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.in("id",userIdList);
        // 1,3,2
        // 1 -> User1 2->User2 3->User3
        Map<Long, List<User>> userIdUserListMap = this.list(userQueryWrapper)
                .stream()
                .map(user -> getSafetyUser(user))
                .collect(Collectors.groupingBy(User::getId));
        List<User> finalUserList = new ArrayList<>();
        for (Long userId : userIdList) {
            // 得到User, get(0) 表示第一个User（也只有一个User）
            finalUserList.add(userIdUserListMap.get(userId).get(0));
        }
        return finalUserList;
    }


    @Override
    public User getLoginUser(HttpServletRequest request) {
        if(request == null){
            return null;
        }
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        if(userObj == null){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        return (User) userObj;
    }
}




