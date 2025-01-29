package com.gruiz.projeto_java.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
@OpenAPIDefinition(info = 
@io.swagger.v3.oas.annotations.info.Info(title = "Documentação API Banco JAVER",
	version = "v1",
	description = "Documentação da API do Banco JAVER"))
public class OpenApiConfiguration {
	
	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				.info(
						new Info()
								.title("Banco JAVER v1.0")
								.description("API para gerenciar os clientes do Banco JAVER")
								.version("v1")
								.license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0"))
				);
	}
}