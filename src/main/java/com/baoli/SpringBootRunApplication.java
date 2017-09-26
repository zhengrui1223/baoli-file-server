package com.baoli;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.context.annotation.Import;

@Import(FdfsClientConfig.class)
@SpringBootApplication
@MapperScan("com.baoli.mapper")	//或者直接在Mapper类上面添加注解@Mapper
@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
public class SpringBootRunApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRunApplication.class, args);
	}
}
