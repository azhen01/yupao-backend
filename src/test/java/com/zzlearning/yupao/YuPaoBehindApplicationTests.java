package com.zzlearning.yupao;

import com.zzlearning.yupao.model.domain.User;
import com.zzlearning.yupao.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

@SpringBootTest
class YuPaoBehindApplicationTests {

    @Resource
    private UserService userService;

    @Test
    void testDigst(){
        String result = DigestUtils.md5DigestAsHex(("abcd" + "password").getBytes());
        System.out.println(result);
    }
//一个 not null 改半天，服了

    /**
     * 测试注册
     */
    @Test
    void userRegister(){
        String userAccount = "zhang1";
        String userPassword = "";
        String checkPassword = "123456";
        String planetCode = "1";
        long result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1,result);
        userAccount = "zzlearning";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1,result);
        userAccount = "zhang";
        userPassword = "123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1,result);
        userAccount = "zha ng";
        userPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1,result);
        checkPassword = "123456789";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1,result);
        userAccount = "dogzhangzhen";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1,result);
        userAccount = "zhang1";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1,result);
    }



    @Test
    void contextLoads() {
        User user = new User();

        user.setUsername("zzlearnings");
        user.setUserAccount("zhangzhen");
        user.setAvatarUrl("https://pic.code-nav.cn/user_avatar/1601072287388278786/9vqTr3HM-WechatIMG1287.jpeg");
        user.setGender(0);
        user.setUserPassword("12345678");
        user.setPhone("123");
        user.setEmail("456");
        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

}
