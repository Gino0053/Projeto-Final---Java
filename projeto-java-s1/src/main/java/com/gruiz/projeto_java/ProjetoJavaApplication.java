package com.gruiz.projeto_java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProjetoJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoJavaApplication.class, args);
	}

}
