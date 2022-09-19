package com.example.exceldemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 注意使用org.mybatis.spring.annotation.MapperScan包下的此注解会连带扫描自定义的MyMapper,会出现
 * java.lang.NoSuchMethodException: tk.mybatis.mapper.provider.base.BaseSelectProvider.异常
 *
 * @author qzz
 */
@SpringBootApplication
@MapperScan("com.example.exceldemo.dao")
public class ExcelDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExcelDemoApplication.class, args);
    }

}
