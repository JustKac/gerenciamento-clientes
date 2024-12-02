package br.com.fsbr.gerenciamento_clientes.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fsbr.gerenciamento_clientes.model.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByNomeContainingIgnoreCase(String nome);
    
}