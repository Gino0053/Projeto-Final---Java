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

import com.gruiz.projeto_java.entity.Cliente;
import com.gruiz.projeto_java.exception.CheckCorrentistaException;
import com.gruiz.projeto_java.service.ClienteService;
import com.gruiz.projeto_java.web.controller.dto.ClienteCreateDTO;
import com.gruiz.projeto_java.web.controller.dto.ClienteResponseDTO;
import com.gruiz.projeto_java.web.controller.dto.mapper.ClienteMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> create(@RequestBody @Valid ClienteCreateDTO dto) throws CheckCorrentistaException{
        Cliente cliente = ClienteMapper.toCliente(dto);
        clienteService.salvar(cliente);

        return ResponseEntity.status(201).body(ClienteMapper.toDTO(cliente));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> getById(@PathVariable Long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        return ResponseEntity.ok(ClienteMapper.toDTO(cliente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> deleteById(@PathVariable Long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        clienteService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> updateCliente(@PathVariable Long id, @Valid @RequestBody ClienteCreateDTO dto) {
        Cliente cliente = clienteService.editarCliente(id, dto);
        return ResponseEntity.ok(ClienteMapper.toDTO(cliente));
    }
    
    @GetMapping("/score/{id}")
    public ResponseEntity<Map<String, Float>> getScoreById(@PathVariable Long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        Map<String, Float> response = new HashMap<>();
        response.put("ScoreCredito", cliente.getScoreCredito());
        return ResponseEntity.ok(response);
    }
}
    