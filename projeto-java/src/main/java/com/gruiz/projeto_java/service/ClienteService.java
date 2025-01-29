package com.gruiz.projeto_java.service;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gruiz.projeto_java.entity.Cliente;
import com.gruiz.projeto_java.exception.ClientNotFoundException;
import com.gruiz.projeto_java.exception.TelefoneUniqueViolationException;
import com.gruiz.projeto_java.repository.ClienteRepository;
import com.gruiz.projeto_java.shortcut.ClienteShortcut;
import com.gruiz.projeto_java.web.controller.dto.ClienteCreateDTO;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public Cliente salvar(Cliente cliente) {
        cliente = ClienteShortcut.verificaCliente(cliente);
        try {
            return clienteRepository.save(cliente);
        } catch (DataIntegrityViolationException ex) {
            throw new TelefoneUniqueViolationException(String.format("Cliente com número de telefone '%s' já existe no sistema.", cliente.getTelefone()));
        }
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
    }

    @Transactional
    public void deletarPorId(Long id) {
        clienteRepository.deleteById(id);
    }

    @Transactional
    public Cliente editarCliente(Long id, ClienteCreateDTO dto) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(
            () -> new ClientNotFoundException(String.format("Cliente id=%s não encontrado no sistema", id))
        );

        if (clienteRepository.existsByTelefoneAndIdNot(dto.getTelefone(), id)) {
            throw new TelefoneUniqueViolationException(String.format("O telefone '%s' já está cadastrado para outro cliente.", dto.getTelefone()));
        }

        cliente.setNome(dto.getNome());
        cliente.setTelefone(dto.getTelefone());
        cliente.setCorrentista(dto.getCorrentista());
        cliente.setSaldoCc(dto.getSaldoCc());

        cliente = ClienteShortcut.verificaCliente(cliente);

        return clienteRepository.save(cliente);
    }
}
