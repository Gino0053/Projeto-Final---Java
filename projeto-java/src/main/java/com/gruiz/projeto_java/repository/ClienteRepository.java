package com.gruiz.projeto_java.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gruiz.projeto_java.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	Optional<Cliente> findByTelefone(Long telefone);

	boolean existsByTelefoneAndIdNot(Long telefone, Long id);

}
