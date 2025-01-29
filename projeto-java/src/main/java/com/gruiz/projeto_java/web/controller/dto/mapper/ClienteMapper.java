package com.gruiz.projeto_java.web.controller.dto.mapper;

import org.modelmapper.ModelMapper;

import com.gruiz.projeto_java.entity.Cliente;
import com.gruiz.projeto_java.web.controller.dto.ClienteCreateDTO;
import com.gruiz.projeto_java.web.controller.dto.ClienteResponseDTO;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteMapper {

	private ClienteMapper() {}
	
	public static Cliente toCliente(ClienteCreateDTO dto) {
		return new ModelMapper().map(dto, Cliente.class);
	}
	
	public static ClienteResponseDTO toDTO(Cliente cliente) {
		return new ModelMapper().map(cliente, ClienteResponseDTO.class);
	}
}