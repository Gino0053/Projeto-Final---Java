package com.gruiz.projeto_java;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gruiz.projeto_java.proxy.ClienteProxy;
import com.gruiz.projeto_java.response.ClienteCreateDTO;
import com.gruiz.projeto_java.response.ClienteResponseDTO;
import com.gruiz.projeto_java.web.controller.ClienteController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.Map;

@WebMvcTest(ClienteController.class)
public class ClienteIT_s1 {

    private static final ObjectMapper objectMapper = new ObjectMapper(); 
	
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClienteProxy clienteProxy;

    @Test
    public void criarCliente_comDadosValidos_RetornarStatusCode201() throws Exception {
        ClienteCreateDTO dto = new ClienteCreateDTO("Ana Souza", 19987654321L, true, 4500.0f);

        when(clienteProxy.createCliente(any(ClienteCreateDTO.class)))
            .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(dto));

        mockMvc.perform(post("/api/v1/clientes")
                .contentType("application/json")
                .content("{\"nome\": \"Ana Souza\", \"telefone\": \"19987654321\", \"correntista\": true, \"saldoCc\": 4500.0}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Ana Souza"))
                .andExpect(jsonPath("$.telefone").value("19987654321"))
                .andExpect(jsonPath("$.correntista").value(true))
                .andExpect(jsonPath("$.saldoCc").value(4500.0))
                .andDo(print());
    }

    @Test
    public void criarCliente_comDadosConflitantes_RetornarStatusCode409() throws Exception {
        ClienteCreateDTO dto = new ClienteCreateDTO("Ana Souza", 19987654321L, true, 4500.0f);

        when(clienteProxy.createCliente(any(ClienteCreateDTO.class)))
            .thenReturn(ResponseEntity.status(HttpStatus.CONFLICT).body(dto));

        mockMvc.perform(post("/api/v1/clientes")
                .contentType("application/json")
                .content("{\"nome\": \"Ana Souza\", \"telefone\": \"19987654321\", \"correntista\": true, \"saldoCc\": 4500.0}"))
                .andExpect(status().isConflict());
    }

    @Test
    public void obterCliente_porId_RetornarStatusCode200() throws Exception {
        mockMvc.perform(get("/api/v1/clientes/1")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void obterScoreCredito_clienteExistente_RetornarStatusCode200() throws Exception {
        Map<String, Float> response = new HashMap<>();
        response.put("scoreCredito", 350.0f);

        when(clienteProxy.getClienteById(1L))
            .thenReturn(ResponseEntity.ok(new ClienteResponseDTO(1L, "João Pereira", 9876543210L, false, 350.0f, 3500.0f)));

        mockMvc.perform(get("/api/v1/clientes/score/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.scoreCredito").value(350.0f));
    }


    @Test
    public void atualizarCliente_comDadosValidos_RetornarStatusCode200() throws Exception {
        ClienteCreateDTO dto = new ClienteCreateDTO("Ana Souza", 19987654321L, true, 4500.0f);

        mockMvc.perform(put("/api/v1/clientes/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    public void excluirCliente_comIdValido_RetornarStatusCode204() throws Exception {
        mockMvc.perform(delete("/api/v1/clientes/1")
                .contentType("application/json"))
                .andExpect(status().isNoContent());
    }
}
