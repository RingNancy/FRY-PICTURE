package com.rin.rinpicturebackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.rin.rinpicturebackend.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
public class RinPictureBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(RinPictureBackendApplication.class, args);
    }

}
