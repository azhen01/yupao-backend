package com.zzlearning.yupao.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzlearning.yupao.model.domain.User;
import com.zzlearning.yupao.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 预热缓存任务
 */
@Component
@Slf4j
public class PreCacheJob {

    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private RedissonClient redissonClient;

    //重点用户
    private List<Long> mainUserList = Arrays.asList(1L);

    //每天执行,预热推荐用户
    @Scheduled(cron = "0 37 16 * * *")
    public void doCacheRecommendUser(){
        RLock lock = redissonClient.getLock("yupao:precache:docache:lock");
        try {
            if (lock.tryLock(0,-1,TimeUnit.MILLISECONDS)){
                System.out.println("getLock:" + Thread.currentThread().getId());
                for (Long userId : mainUserList) {
                    QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
                    Page<User> userPage = userService.page(new Page<>(1, 20), userQueryWrapper);
                    String redisKey = String.format("yupao:user:recommend:%s",userId);
                    ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
                    try {
                        valueOperations.set(redisKey,userPage,30000, TimeUnit.MILLISECONDS);
                    } catch (Exception e) {
                        log.error("redisSetKey error", e);
                    }
                }
            }
        } catch (InterruptedException e) {
            log.error("doCachePreRecommendUser",e);
        } finally {
            //只能释放自己的锁
            if (lock.isHeldByCurrentThread()){
                System.out.println("unlock:" + Thread.currentThread().getId());
                lock.unlock();
            }

        }
    }
}
