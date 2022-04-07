package com.SnHI.server;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.SnHI.server.mapper")
// 开启定时任务
@EnableScheduling
// 将Fdfs配置引入项目
@Import(FdfsClientConfig.class)
public class CloudEServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudEServerApplication.class,args);
    }

}
