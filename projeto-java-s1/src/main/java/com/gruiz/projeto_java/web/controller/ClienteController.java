package com.gruiz.projeto_java.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gruiz.projeto_java.proxy.ClienteProxy;
import com.gruiz.projeto_java.response.ClienteCreateDTO;
import com.gruiz.projeto_java.response.ClienteResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/clientes")
@Tag(name = "Banco JAVER", description = "API para gerenciamento de clientes no Banco JAVER")
public class ClienteController {

    private final ClienteProxy clienteProxy;

    public ClienteController(ClienteProxy clienteProxy) {
        this.clienteProxy = clienteProxy;
    }

    @Operation(
        summary = "Criar um novo cliente",
        description = "Cria um cliente com os dados fornecidos no corpo da requisição",
        responses = {
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "409", description = "Telefone já cadastrado.")
        }
    )
    @PostMapping
    public ResponseEntity<ClienteCreateDTO> create(
        @RequestBody @Valid ClienteCreateDTO dto) {
        return clienteProxy.createCliente(dto);
    }

    @Operation(
        summary = "Obter cliente por ID",
        description = "Obtém os detalhes de um cliente baseado no ID fornecido",
        responses = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> getById(
        @PathVariable Long id) {
        return clienteProxy.getClienteById(id);
    }

    @Operation(
        summary = "Obter o score de crédito do cliente",
        description = "Obtém o score de crédito do cliente baseado no ID fornecido",
        responses = {
            @ApiResponse(responseCode = "200", description = "Score de crédito encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
        }
    )
    @GetMapping("/score/{id}")
    public ResponseEntity<Map<String, Float>> getScoreCredito(
        @Parameter(description = "ID do cliente para obter o score de crédito") @PathVariable Long id) {
        ResponseEntity<ClienteResponseDTO> cliente = clienteProxy.getClienteById(id);

        if (!cliente.getStatusCode().is2xxSuccessful() || cliente.getBody() == null) {
            return ResponseEntity.notFound().build();
        }
        Float scoreCredito = cliente.getBody().getScoreCredito();

        Map<String, Float> response = new HashMap<>();
        response.put("scoreCredito", scoreCredito);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Atualizar dados do cliente",
        description = "Atualiza os dados do cliente baseado no ID fornecido",
        responses = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
            @ApiResponse(responseCode = "400", description = "Cliente não pode ter saldo na conta corrente não sendo correntista, ou nome menor que 4 digitos"),
            @ApiResponse(responseCode = "409", description = "Telefone já cadastrado.")
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> updateCliente(
        @PathVariable Long id,
        @Valid @RequestBody ClienteCreateDTO dto) {
        return clienteProxy.updateCliente(id, dto);
    }

    @Operation(
        summary = "Excluir um cliente",
        description = "Exclui o cliente baseado no ID fornecido",
        responses = {
            @ApiResponse(responseCode = "204", description = "Cliente excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> deleteById(
        @PathVariable Long id) {
        clienteProxy.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }
}
