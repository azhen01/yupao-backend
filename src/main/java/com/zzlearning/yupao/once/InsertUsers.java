package com.zzlearning.yupao.once;

import com.zzlearning.yupao.mapper.UserMapper;
import com.zzlearning.yupao.model.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;

@Component
public class InsertUsers {

    @Resource
    private UserMapper userMapper;

    /**
     * 批量插入用户
     */
//    @Scheduled(initialDelay = 5000,fixedDelay = Long.MAX_VALUE)
    public void doInsertUsers(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int INSERT_NUM = 1000;
        for(int i = 0; i < INSERT_NUM;i++){
            User user = new User();
            user.setUsername("假zhangzhen");
            user.setUserAccount("fakezhangzhen");
            user.setAvatarUrl("https://pic.code-nav.cn/user_avatar/1601072287388278786/9vqTr3HM-WechatIMG1287.jpeg");
            user.setGender(0);
            user.setUserPassword("12345678");
            user.setPhone("123");
            user.setEmail("345@qq.com");
            user.setTags("[]");
            user.setUserRole(0);
            user.setUserStatus(0);
            user.setPlanetCode("111111");
            userMapper.insert(user);
        }
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
    }
}
