package com.gruiz.projeto_java.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

import com.gruiz.projeto_java.response.ClienteResponseDTO;
import com.gruiz.projeto_java.response.ClienteCreateDTO;

import jakarta.validation.Valid;

@FeignClient(name = "projeto-java", url = "http://localhost:8080/api/v1/clientes")
public interface ClienteProxy {

    @PostMapping
    ResponseEntity<ClienteCreateDTO> createCliente(@Valid ClienteCreateDTO dto);
	
    @GetMapping("/{id}")
	ResponseEntity<ClienteResponseDTO> getClienteById(@PathVariable Long id);
    
    @DeleteMapping("/{id}")
	void deleteCliente(@PathVariable Long id);

    @PutMapping("/{id}")
	ResponseEntity<ClienteResponseDTO> updateCliente(@PathVariable Long id, @RequestBody @Valid ClienteCreateDTO dto);}
