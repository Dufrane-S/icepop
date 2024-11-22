package com.ssafy.icecreamapp;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan("com.ssafy.icecreamapp.model.dao")
public class IcecreamappApplication {

	public static void main(String[] args) {
		SpringApplication.run(IcecreamappApplication.class, args);
	}
}