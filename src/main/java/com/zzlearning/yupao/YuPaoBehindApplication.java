package com.zzlearning.yupao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.zzlearning.yupao.mapper")
@EnableScheduling
public class YuPaoBehindApplication {

    public static void main(String[] args) {
        SpringApplication.run(YuPaoBehindApplication.class, args);
    }

}
