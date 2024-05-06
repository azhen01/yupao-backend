package com.zzlearning.yupao.service;

import com.zzlearning.yupao.model.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@SpringBootTest
public class InsertUsersTest {
    @Resource
    private UserService userService;

    private ExecutorService executorService = new ThreadPoolExecutor(40,1000,10000, TimeUnit.MINUTES,new ArrayBlockingQueue<>(10000));

    /**
     * 批量插入用户
     */
    @Test
    public void doInsertUsers() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int INSERT_NUM = 1000;
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < INSERT_NUM; i++) {
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
            userList.add(user);
        }
        userService.saveBatch(userList,100);
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
    }

    @Test
    public void doConcurrencyInsertUsers() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        int batchSize = 2500;
        int j = 0;
        List<CompletableFuture<Void>> futureList = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            List<User> userList =  new ArrayList<>();
            while(true){
                j++;
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
                userList.add(user);
                if(j % 10000 == 0){
                    break;
                }
            }
            CompletableFuture<Void> future = CompletableFuture.runAsync(() ->{
                System.out.println("threadName: " + Thread.currentThread().getName());
                userService.saveBatch(userList,batchSize);
            });
            futureList.add(future);
        }
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[]{})).join();
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
    }
}
