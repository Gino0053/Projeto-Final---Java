package com.gruiz.projeto_java;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.gruiz.projeto_java.web.exception.ErrorResponse;

import com.gruiz.projeto_java.web.controller.dto.ClienteCreateDTO;
import com.gruiz.projeto_java.web.controller.dto.ClienteResponseDTO;

@Sql(scripts = "/sql/clientes/clientes-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/clientes/clientes-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClienteIT {
	
	@Autowired
	WebTestClient testClient;
	
	@Test
	public void criarCliente_ComDadosValidos_RetornarComStatus201() {
		ClienteCreateDTO clienteCreateDto = new ClienteCreateDTO(
			    "Clodoaldo",
			    19989619850L,
			    true,
			    3000.0f
			);

			ClienteResponseDTO responseBody = testClient
			    .post()
			    .uri("/api/v1/clientes")
			    .contentType(MediaType.APPLICATION_JSON)
			    .bodyValue(clienteCreateDto)
			    .exchange()
			    .expectStatus().isCreated()
			    .expectBody(ClienteResponseDTO.class)
			    .returnResult().getResponseBody();

			org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
			org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
			org.assertj.core.api.Assertions.assertThat(responseBody.getNome()).isEqualTo("Clodoaldo");
			org.assertj.core.api.Assertions.assertThat(responseBody.getCorrentista()).isTrue();
			org.assertj.core.api.Assertions.assertThat(responseBody.getTelefone()).isEqualTo(19989619850L);
			org.assertj.core.api.Assertions.assertThat(responseBody.getScoreCredito()).isEqualTo(300.0f);
			
			clienteCreateDto = new ClienteCreateDTO(
				    "Valdomiro",
				    19989619841L,
				    true,
				    700.0f
				);

				responseBody = testClient
				    .post()
				    .uri("/api/v1/clientes")
				    .contentType(MediaType.APPLICATION_JSON)
				    .bodyValue(clienteCreateDto)
				    .exchange()
				    .expectStatus().isCreated()
				    .expectBody(ClienteResponseDTO.class)
				    .returnResult().getResponseBody();

				org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
				org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
				org.assertj.core.api.Assertions.assertThat(responseBody.getNome()).isEqualTo("Valdomiro");
				org.assertj.core.api.Assertions.assertThat(responseBody.getCorrentista()).isTrue();
				org.assertj.core.api.Assertions.assertThat(responseBody.getTelefone()).isEqualTo(19989619841L);
				org.assertj.core.api.Assertions.assertThat(responseBody.getScoreCredito()).isEqualTo(200.0f);
				
				clienteCreateDto = new ClienteCreateDTO(
					    "Carlinhos",
					    19989619848L,
					    false
					);

					responseBody = testClient
					    .post()
					    .uri("/api/v1/clientes")
					    .contentType(MediaType.APPLICATION_JSON)
					    .bodyValue(clienteCreateDto)
					    .exchange()
					    .expectStatus().isCreated()
					    .expectBody(ClienteResponseDTO.class)
					    .returnResult().getResponseBody();

					org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
					org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
					org.assertj.core.api.Assertions.assertThat(responseBody.getNome()).isEqualTo("Carlinhos");
					org.assertj.core.api.Assertions.assertThat(responseBody.getCorrentista()).isFalse();
					org.assertj.core.api.Assertions.assertThat(responseBody.getTelefone()).isEqualTo(19989619848L);
					org.assertj.core.api.Assertions.assertThat(responseBody.getScoreCredito()).isEqualTo(200.0f);
	}

	@Test
	public void criarCliente_ComDadosInvalidos_RetornarComStatus400() {
		ClienteCreateDTO clienteCreateDto = new ClienteCreateDTO(
				"Clod",
				19989619851L,
				true,
				3000.0f
				);
		
		ErrorResponse responseBody = testClient
				.post()
				.uri("/api/v1/clientes")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(clienteCreateDto)
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody(ErrorResponse.class)
				.returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		
		clienteCreateDto = new ClienteCreateDTO(
				"Valdomiro",
				19989619842L,
				false,
				700.0f
				);
		
		responseBody = testClient
				.post()
				.uri("/api/v1/clientes")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(clienteCreateDto)
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody(ErrorResponse.class)
				.returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		
	}

	@Test
	public void criarCliente_ComTelefoneEmConflito_RetornarComStatus409() {
		ClienteCreateDTO clienteCreateDto = new ClienteCreateDTO(
				"Clodoaldo",
				11543210987L,
				true,
				3000.0f
				);
		
		ErrorResponse responseBody = testClient
				.post()
				.uri("/api/v1/clientes")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(clienteCreateDto)
				.exchange()
				.expectStatus().isEqualTo(409)
				.expectBody(ErrorResponse.class)
				.returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
	}
	
	@Test
	public void recuperarCliente_ComIdExistente_RetornarComStatus200() {
		
		ClienteResponseDTO responseBody = testClient
				.get()
				.uri("/api/v1/clientes/1003")
				.exchange()
				.expectStatus().isOk()
				.expectBody(ClienteResponseDTO.class)
				.returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getNome()).isEqualTo("Eduardo Souza");
		org.assertj.core.api.Assertions.assertThat(responseBody.getCorrentista()).isTrue();
		org.assertj.core.api.Assertions.assertThat(responseBody.getTelefone()).isEqualTo(11654321098L);
		org.assertj.core.api.Assertions.assertThat(responseBody.getScoreCredito()).isEqualTo(200.0f);
	}

	@Test
	public void recuperarCliente_ComIdInexistente_RetornarComStatus404() {
		
		ErrorResponse responseBody = testClient
				.get()
				.uri("/api/v1/clientes/1010")
				.exchange()
				.expectStatus().isNotFound()
				.expectBody(ErrorResponse.class)
				.returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		
	}
	
	@Test
	public void recuperarScore_ComIdInexistente_RetornarComStatus404() {
		
		ErrorResponse responseBody = testClient
				.get()
				.uri("/api/v1/clientes/score/1010")
				.exchange()
				.expectStatus().isNotFound()
				.expectBody(ErrorResponse.class)
				.returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		
	}

	@Test
	public void recuperarScore_ComIdExistente_RetornarComStatus200() {
		
		Map<String, Float> responseBody = testClient
			    .get()
			    .uri("/api/v1/clientes/score/1003")
			    .exchange()
			    .expectStatus().isOk()
			    .expectBody(new ParameterizedTypeReference<Map<String, Float>>() {})
			    .returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.get("ScoreCredito")).isEqualTo(200f);
	}

	@Test
	public void excluirCliente_ComIdExistente_RetornarComStatus200() {
		
		testClient
				.delete()
				.uri("/api/v1/clientes/1001")
				.exchange()
				.expectStatus().isNoContent();
		}

	@Test
	public void excluirCliente_ComIdInexistente_RetornarComStatus404() {
		
		ErrorResponse responseBody = testClient
				.delete()
				.uri("/api/v1/clientes/1011")
				.exchange()
				.expectStatus().isNotFound()
				.expectBody(ErrorResponse.class)
				.returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		
	}
	
	@Test
	public void atualizarCliente_ComDadosValidos_RetornarComStatus200() {
		ClienteCreateDTO clienteCreateDto = new ClienteCreateDTO(
			    "Joana Pereira",
			    11987654321L,
			    true,
			    2000.0f
			);

			ClienteResponseDTO responseBody = testClient
			    .put()
			    .uri("/api/v1/clientes/1000")
			    .contentType(MediaType.APPLICATION_JSON)
			    .bodyValue(clienteCreateDto)
			    .exchange()
			    .expectStatus().isOk()
			    .expectBody(ClienteResponseDTO.class)
			    .returnResult().getResponseBody();

			org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
			org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
			org.assertj.core.api.Assertions.assertThat(responseBody.getNome()).isEqualTo("Joana Pereira");
			org.assertj.core.api.Assertions.assertThat(responseBody.getCorrentista()).isTrue();
			org.assertj.core.api.Assertions.assertThat(responseBody.getTelefone()).isEqualTo(11987654321L);
			org.assertj.core.api.Assertions.assertThat(responseBody.getScoreCredito()).isEqualTo(200.0f);
	}
	
	@Test
	public void atualizarCliente_ComDadosInvalidos_RetornarComStatus400() {
		ClienteCreateDTO clienteCreateDto = new ClienteCreateDTO(
				"Joan",
			    11987654321L,
			    true,
			    2000.0f
				);
		
		ErrorResponse responseBody = testClient
				.put()
				.uri("/api/v1/clientes/1000")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(clienteCreateDto)
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody(ErrorResponse.class)
				.returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		
	}
	
	@Test
	public void updateCliente_ComTelefoneExistente_RetornarErrorComStatus409() {
		ClienteCreateDTO clienteCreateDto = new ClienteCreateDTO(
				"Joana Pereira",
				11654321098L,
			    true,
			    2000.0f
			);

			ErrorResponse responseBody = testClient
			    .put()
			    .uri("/api/v1/clientes/1000")
			    .contentType(MediaType.APPLICATION_JSON)
			    .bodyValue(clienteCreateDto)
			    .exchange()
			    .expectStatus().isEqualTo(409)
			    .expectBody(ErrorResponse.class)
			    .returnResult().getResponseBody();

			org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
	}
	
	@Test
	public void updateCliente_ComIdInexistente_RetornarErrorComStatus404() {
		ClienteCreateDTO clienteCreateDto = new ClienteCreateDTO(
				"Joana Pereira",
			    11987654321L,
			    true,
			    2000.0f
			);

			ErrorResponse responseBody = testClient
			    .put()
			    .uri("/api/v1/clientes/1000000")
			    .contentType(MediaType.APPLICATION_JSON)
			    .bodyValue(clienteCreateDto)
			    .exchange()
			    .expectStatus().isNotFound()
			    .expectBody(ErrorResponse.class)
			    .returnResult().getResponseBody();

			org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
	}
}