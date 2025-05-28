package com.jmcurso.cource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
	    info = @Info(title = "API Flash - Jefferson Moreno", version = "2.0", description = "Documentação da API")
	)

@SpringBootApplication
public class CourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourceApplication.class, args);
	}
}
