package com.asia;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
@MapperScan("com.asia.mapper")
public class GetdataApplication {

	public static void main(String[] args) {
		SpringApplication.run(GetdataApplication.class, args);
	}
}
